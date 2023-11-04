package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class bookFlowAnalyticsController implements Initializable {

	@FXML
	private VBox rootVBox;
	@FXML
	private BarChart<String, Number> readsGraph;
	@FXML
	public ImageView backToMainWindow;

	private GoogleBooksApiClient apiClient;
	private String maxResultsPerPage = "40";
	String genres[] = { "Mystery", "Thriller", "Science-Fiction","Self-Help", "Cooking", "Science",
			 "History", "Poetry", "Comics", "Children", "Mythology", "Crime", "Cyberpunk", "Post-Apocalyptic", "Urban-Fantasy" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Random random = new Random();
		int randomIndex = random.nextInt(genres.length - 1);
		String selectedGenre = genres[randomIndex];
		String query = selectedGenre;
		System.out.println(query);
		apiClient = new GoogleBooksApiClient();

		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis(0, 5, 0.5);
		xAxis.setAnimated(false);
		yAxis.setAnimated(false);
		readsGraph.setAnimated(true);

		XYChart.Series<String, Number> series = new XYChart.Series<>();

		List<Book> books = apiClient.fetchBooksData(query, maxResultsPerPage);

		List<Tooltip> tooltips = new ArrayList<>();

		for (Book book : books) 
		{
			double rating = book.getRating();
			if (rating > 0.0) {
				String bookName = book.getTitle();

				XYChart.Data<String, Number> data = new XYChart.Data<>(bookName, rating);

				// adding the data to the chart (series)
				series.getData().add(data);

				Tooltip tool = new Tooltip(bookName + "\n" + rating);
				tool.setShowDelay(Duration.millis(100));
				tooltips.add(tool);
			}
		}

		readsGraph.setPrefHeight(500);
		readsGraph.setPrefWidth(100);

		readsGraph.getData().add(series);

		Platform.runLater(() -> 
		{
			List<XYChart.Data<String, Number>> dataPoints = series.getData();
			for (int i = 0; i < dataPoints.size(); i++) {
				Tooltip tooltip = tooltips.get(i);
				Tooltip.install(dataPoints.get(i).getNode(), tooltip);
				dataPoints.get(i).getNode().setOnMouseClicked(event->System.out.println("you clicked the graph with having book name " + tooltip.getText()));
			}
		
		});
	}

}