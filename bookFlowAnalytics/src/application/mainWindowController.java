package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class mainWindowController implements Initializable {

	@FXML
	private GridPane centerBox;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	public ImageView graphSceneButton;
	@FXML
	public TextField searchField;
	@FXML
	private Button searchButton;
	@FXML
	private ProgressIndicator loadingSpinner;
	@FXML
	private ImageView homeButton;

	public ImageView coverPage;
	private GoogleBooksApiClient apiClient;
	private boolean testingmode = false;
	//	private boolean testingmode = true;

	private ImageView clickedBook;
	private String title;
	private String author;
	private String publicationDate;
	private int ratingsCount;
	private double rating;
	private String description;
	private String maxResultsPerPage = "20";
	private String query;
	String genres[] = {
			"Mystery", "Thriller","Science-Fiction", "Fantasy",
			"Self-Help", "Cooking", "Science","Technology", "History",
			"Art", "Poetry", "Comics", "Children",
			"Mythology","Western", "Crime", "Medical", "Political",
			"Spy","Western", "Cyberpunk", "Post-Apocalyptic", "Steampunk",
			"Urban-Fantasy" 
	};
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Tooltip tooltip = new Tooltip("home");
		tooltip.setShowDelay(Duration.millis(100));
		Tooltip.install(homeButton, tooltip);
		loadingSpinner.setVisible(false);
		apiClient = new GoogleBooksApiClient();
		Random random = new Random();
		int randomIndex = random.nextInt(genres.length - 1);
		String selectedGenre = genres[randomIndex];
		query = selectedGenre;

		if (testingmode == false) 
		{
			List<Book> bookdata = apiClient.fetchBooksData(query,maxResultsPerPage);

			int row = 0;
			int col = 0;

			for (Book book : bookdata) 
			{
				coverPage = new ImageView(new Image(book.getCoverUrl()));
				coverPage.setFitHeight(150);
				coverPage.setPreserveRatio(true);

				coverPage.setOnMouseClicked(Event -> check(book,coverPage));

				coverPage.getStyleClass().add("cover-image");


				GridPane.setRowIndex(coverPage, row);
				GridPane.setColumnIndex(coverPage, col);

				centerBox.getChildren().add(coverPage);

				col++;
				if (col > 3) 
				{ // Display 3 books per row
					col = 0;
					row++;

					if (row > 4) 
					{
						break;
					}
				}

			}
		}

	}
	public void check(Book book, ImageView clickedCoverPage)
	{
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.publicationDate = book.getPubDate();
		this.ratingsCount = book.getRatingsCount();
		this.rating = book.getRating();
		this.description = book.getDescription();
		this.clickedBook = clickedCoverPage;

		Image coverImage = new Image(book.getCoverUrl());

		FXMLLoader reviewWindowLoad = new FXMLLoader(getClass().getResource("/bookReview.fxml"));
		try {
			Stage stage = new Stage();
			Parent newroot = reviewWindowLoad.load();
			Scene newScene = new Scene(newroot);
			// Access the controller of the loaded FXML
			reviewWindowController reviewController = reviewWindowLoad.getController();
			reviewController.setBookData(title, author, publicationDate, ratingsCount, rating, description,coverImage);

			stage.setScene(newScene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setResizable(false);
			stage.setTitle(title);
			stage.setY(150);
			stage.setX(350);
			stage.show();

			reviewController.closeButton.setOnMouseClicked(Event ->{
				stage.close();
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void performSearch()
	{
		PerformSearchTask performSearchTask = new PerformSearchTask(apiClient,
				maxResultsPerPage, centerBox, searchField, loadingSpinner, 
				searchButton, coverPage,this);

		Thread th = new Thread(performSearchTask);
		th.setDaemon(true);
		th.start();
		Main.isOnHomeScreen = false;
	}
	public ProgressIndicator getLoadingSpinner() {
		return loadingSpinner;
	}
	public GridPane getCenterBox() {
		return centerBox;
	}
	public String getQuery() {
		return query;
	}
	public ImageView getHomeButton() {
		return homeButton;
	}
	public ImageView getClickedBook() {
		return clickedBook;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getPublicationDate() {
		return publicationDate;
	}
	public int getRatingsCount() {
		return ratingsCount;
	}
	public double getRating() {
		return rating;
	}
	public String getDescription() {
		return description;
	}
}
