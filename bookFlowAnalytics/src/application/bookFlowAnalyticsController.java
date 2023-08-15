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

	// Injecting FXML in the controller
	@FXML
	private VBox rootVBox;
	@FXML
	private BarChart<String, Number> readsGraph;
	@FXML
	public ImageView backToMainWindow;

	private GoogleBooksApiClient apiClient;

	private String maxResultsPerPage = "30";
	String genres[] = { "Mystery", "Thriller", "Science-Fiction", "Fantasy", "Self-Help", "Cooking", "Science",
			"Technology", "History", "Art", "Poetry", "Comics", "Children", "Mythology", "Western", "Crime", "Medical",
			"Political", "Spy", "Western", "Cyberpunk", "Post-Apocalyptic", "Steampunk", "Urban-Fantasy" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// getting a random genre every time the user clicks on the button and therefore
		// would see a new graph every time he opens this window
		Random random = new Random();
		int randomIndex = random.nextInt(genres.length - 1);
		String selectedGenre = genres[randomIndex];
		String query = selectedGenre;

		// instantiating the GoogleBooksApiClient class
		apiClient = new GoogleBooksApiClient();

		// setting the the x and y axis for the bar chart graph
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis(0, 5, 0.5);
		xAxis.setAnimated(false);
		yAxis.setAnimated(false);
		readsGraph.setAnimated(true);

		XYChart.Series<String, Number> series = new XYChart.Series<>();

		// fetching data for the graph, just one thing is going to be different here,
		// the max results here are set to 30 as many books don't have rating so more
		// amount of data is being
		// made fetched
		List<Book> books = apiClient.fetchBooksData(query, maxResultsPerPage);

		// creating a TooltTip arrayList to add all the newly created ToolTips with different data in each of them
		List<Tooltip> tooltips = new ArrayList<>();

		// again iterating through each book from the fetched data
		for (Book book : books) 
		{
			// getting the rating of the book in the early stages to filter out the books with no rating and avoiding getting a empty graph
			double rating = book.getRating();
			if (rating > 0.0) {
				String bookName = book.getTitle();

				// extracting and setting data which is going to be added in the graph later
				XYChart.Data<String, Number> data = new XYChart.Data<>(bookName, rating);

				// adding the data to the chart (series)
				series.getData().add(data);

				// making a new ToolTip with every iteration and passing data in it as well
				Tooltip tool = new Tooltip(bookName + "\n" + rating);
				tool.setShowDelay(Duration.millis(100));
				tooltips.add(tool);
			}
		}

//		setting the height chart the graph
		readsGraph.setPrefHeight(900);

//		adding the chart to the BarChart
		readsGraph.getData().add(series);

//		Additionally iterating through each node of the data from the series which is basically the single bar in the chart and installing ToolTip to them
		Platform.runLater(() -> 
		{
			List<XYChart.Data<String, Number>> dataPoints = series.getData();
			for (int i = 0; i < dataPoints.size(); i++) {
				Tooltip tooltip = tooltips.get(i);
				Tooltip.install(dataPoints.get(i).getNode(), tooltip);
			}
		});
	}

}