package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

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

//	injecting FXML in the controller
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
	@FXML
	private ImageView loginButtonM;

	public ImageView coverPage;
	private GoogleBooksApiClient apiClient;
	public static Stage loginStage;
	private Stage stage;

//	keeping them, as this helpful while working on the background of the application
	private boolean testingmode = false;
//	 private boolean testingmode = true;

	private ImageView clickedBook;
	private String title;
	private String author;
	private String publicationDate;
	private int ratingsCount;
	private double rating;
	private String description;
	private String maxResultsPerPage = "20";
	private Boolean stageIsShowing = false;
	private String query;
//	genres array to select from
	String genres[] = { "Thriller", "Science-Fiction", "Fantasy", "Self-Help", "Science", "Technology",
			"Art", "Poetry", "Children", "Mythology", "Western", "Crime", "Medical", "Western", "Cyberpunk",
			"Post-Apocalyptic", "Steampunk", "Urban-Fantasy" };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		tooltip for the home button in header
		Tooltip tooltip = new Tooltip("home");
		tooltip.setShowDelay(Duration.millis(100));
		Tooltip.install(homeButton, tooltip);

//		setting the loading spinner invisible originally
		loadingSpinner.setVisible(false);

//		instantiating the GoogleApiClients class
		apiClient = new GoogleBooksApiClient();

//		Using Random library picking a random value from the genres and array and then sending it in the URL
		Random random = new Random();
		int randomIndex = random.nextInt(genres.length - 1);
		String selectedGenre = genres[randomIndex];
		query = selectedGenre;
		System.out.println(query);

//		this if statement is there just to simplify development of the applications visual else this if is useless here
		if (testingmode == false) {
//			Getting the fetched data in a list (here Book is a class which is going to contain the books data and using its in built getters we are going to get the data and then display)
			List<Book> bookdata = apiClient.fetchBooksData(query, maxResultsPerPage);

			int row = 0;
			int col = 0;

//			iterating through each element of the collection 
			int totalbooksdata = 0;
			for (Book book : bookdata) {
//				creating  a new ImageView and getting the coverURL and putting in the ImageView
				totalbooksdata++;
				System.out.println("Total books loaded : " + totalbooksdata);
				coverPage = new ImageView(new Image(book.getCoverUrl()));
				coverPage.setFitHeight(150);
				coverPage.setPreserveRatio(true);

//				assigning an event listener to every cover page which is going to be on the grid
				coverPage.setOnMouseClicked(Event -> check(book, coverPage));

//				assigning each cover page a same CSS style class to make it easier to apply styling to all of the cover pages at once
				coverPage.getStyleClass().add("cover-image");

//				setting index and the ImageView in the grid
				GridPane.setRowIndex(coverPage, row);
				GridPane.setColumnIndex(coverPage, col);

//				Adding each cover page in the grid one by one
				centerBox.getChildren().add(coverPage);

//				incrementing the column so the cover pages are displayed in the next cell every time a new book is being iterated
				col++;

//				if column is incremented to 4 then it will enter this if block and again restart and making the cover page again start from the 0 index
				if (col > 3) {
					col = 0;

//					incrementing row's index so the books start from the row and from the first column
					row++;
					if (row > 4) {
//						breaking through the loop once we achieve the 4 x 5 grid
						break;
					}
				}

			}
		}

		searchField.setOnAction(event -> {
			System.out.println("this is going to execute when hit enter ");
			performSearch();
		});
	}

	public void openLogin() {
		FXMLLoader loginWindowload = new FXMLLoader(getClass().getResource("/loginWindow.fxml"));
		try {
			loginStage = new Stage();
			Parent loginRoot = loginWindowload.load();
			Scene loginScene = new Scene(loginRoot);

			loginWindowController loginController = loginWindowload.getController();
			loginScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			loginStage.setScene(loginScene);
			loginStage.initStyle(StageStyle.TRANSPARENT);
			loginStage.setResizable(false);
			loginStage.setTitle("User Login");
			loginStage.setY(180);
			loginStage.setX(440);
			loginStage.show();

//			added a custom close button image therefore adding a event listener to it to close this window
			loginController.closeButton.setOnMouseClicked(Event -> {
				loginStage.close();
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	this is the method which gets executed when clicked on any of the cover pages
	public void check(Book book, ImageView clickedCoverPage) {
//		to get the details about the book we need to know which book was iterating while passing this function in the event listener and therefore the book parameter was passed
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.publicationDate = book.getPubDate();
		this.ratingsCount = book.getRatingsCount();
		this.rating = book.getRating();
		this.description = book.getDescription();
//		the ImageView parameter was passed only to get the cover page and to display it on this new screen is going to be created
		this.clickedBook = clickedCoverPage;

//		converting the coverURL into an Image
		Image coverImage = new Image(book.getCoverUrl());

//		loading a new stage and new scene for every book which is clicked 
		FXMLLoader reviewWindowLoad = new FXMLLoader(getClass().getResource("/bookReview.fxml"));
		
		try {
			
			if (stageIsShowing) {
				stage.close();
			}
			
			stage = new Stage();
			Parent newroot = reviewWindowLoad.load();
			Scene newScene = new Scene(newroot);
			
//			getting the review window's controller
			reviewWindowController reviewController = reviewWindowLoad.getController();
			
//			passing data using a function so every time a book is clicked the function again runs and shows the new and updated information on the screen
			reviewController.setBookData(title, author, publicationDate, ratingsCount, rating, description, coverImage);
			stage.setScene(newScene);
			
//			making its title bar transparent 
			newScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setResizable(false);
			stage.setTitle(title);
			stage.setY(150);
			stage.setX(350);
			stage.show();

//			added a custom close button image therefore adding a event listener to it to close this window
			reviewController.closeButton.setOnMouseClicked(Event -> {
				stage.close();
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		stageIsShowing = true;
	}

//	this function gets executed once the search button is clicked
	public void performSearch() {
//		creating a new task for fetching the data in the background and then later updating the changes
		PerformSearchTask performSearchTask = new PerformSearchTask(apiClient, maxResultsPerPage, centerBox,
				searchField, loadingSpinner, searchButton, coverPage, this);

//		running the task by making a thread
		Thread th = new Thread(performSearchTask);
		th.setDaemon(true);
		th.start();
		
//		setting this variable to false as now the user is going to be on the screen with a search feed and if he wishes to go back then the image should be listening to the click
		Main.isOnHomeScreen = false;
	}

//	getters for private variables
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