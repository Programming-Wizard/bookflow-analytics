package application;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class bookFlowAnalyticsController implements Initializable {

	@FXML
	private VBox rootVBox;
	@FXML
	private BarChart<String, Number> readsGraph;
	@FXML
	public ImageView backToMainWindow;

	private GoogleBooksApiClient apiClient;

	private String maxResultsPerPage = "20";
	String genres[] = {"Action Adventure", "Mystery", "Thriller","Science-Fiction", "Fantasy",
			"Self-Help", "Cooking", "Science","Technology", "History",
			"Art", "Music", "Poetry", "Graphic Novels", "Comics", "Children", "Classic",
			"Mythology","Western", "Crime", "Medical", "Political",
			"Spy", "War", "Western","Aliens", "Cyberpunk", "Post-Apocalyptic", "Steampunk",
			"Urban-Fantasy" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Random random = new Random();
		int randomIndex = random.nextInt(genres.length - 1);
		String selectedGenre = genres[randomIndex];
		String query = selectedGenre;
		mainWindowController mainWindow = new mainWindowController();

		apiClient = new GoogleBooksApiClient();

		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis(0, 5, 0.5);
		xAxis.setAnimated(false);
		yAxis.setAnimated(false);
		readsGraph.setAnimated(true);
		xAxis.setLabel("Books");
		yAxis.setLabel("Rating");

		XYChart.Series<String, Number> series = new XYChart.Series<>();

		List<Book> books = apiClient.fetchBooksData(query, maxResultsPerPage);

		for (Book book : books) {
			double rating = book.getRating();
			if (rating > 0.0) {
				String bookName = book.getTitle();
				series.getData().add(new XYChart.Data<>(bookName, rating));
			}

		}

		readsGraph.setPrefHeight(900);

		readsGraph.getData().add(series);
	}

}
