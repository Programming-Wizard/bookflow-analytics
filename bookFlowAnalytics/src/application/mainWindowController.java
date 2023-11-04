package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
	@FXML
	private ImageView userAvatar;
	@FXML
	private AnchorPane header;
	@FXML
	private ChoiceBox<String> genreList;
	@FXML
	private Label errorLabel;
	@FXML
	private ImageView errorImage;

	public ImageView coverPage;
	private GoogleBooksApiClient apiClient;
	public static Stage loginStage;
	private Stage stage;
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
	private boolean closedByButton = false;
	public static Stage utilityStage;


	String genres[] = { "Thriller", "Science-Fiction", "Self-Help", "Science", "Art", "Poetry", "Children", "Mythology",
			"Crime", "Medical", "Western", "Cyberpunk", "Post-Apocalyptic", "Steampunk", "Urban-Fantasy" };
	loginWindowController loginMethods = new loginWindowController();

//	keeping them, as this helpful while working on the background of the application
//	private boolean testingmode = false;
	private boolean testingmode = true;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		errorLabel.setText("Insufficient data available to be fetched from the API");
		errorImage.setVisible(false);
		errorLabel.setVisible(false);
		genreList.setValue("Select Genre :");
		genreList.getItems().addAll(genres);
		genreList.getItems().add("Marathi");
		genreList.setOnAction(this::customGenreSearch);
		userAvatar.setVisible(false);

//		tooltip for the home button in header
		Tooltip tooltip = new Tooltip("home");
		tooltip.setShowDelay(Duration.millis(100));
		Tooltip.install(homeButton, tooltip);
//		tooltip for the avatar image


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

			List<Book> bookdata = apiClient.fetchBooksData(query, maxResultsPerPage);

			int row = 0;
			int col = 0;

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

				coverPage.getStyleClass().add("cover-image");

//				setting index and the ImageView in the grid
				GridPane.setRowIndex(coverPage, row);
				GridPane.setColumnIndex(coverPage, col);

//				Adding each cover page in the grid one by one
				centerBox.getChildren().add(coverPage);
				col++;

				if (col > 3) {
					col = 0;

					row++;
					if (row > 4) {
						break;
					}
				}

			}
		}

		searchField.setOnAction(event -> {
			performSearch();
		});
	}

	public void customGenreSearch(ActionEvent event) {

		String requestedGenre = genreList.getValue();

		Task<Void> fetchBooksTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				loadingSpinner.setVisible(true);
				List<Book> bookdata = apiClient.fetchBooksData(requestedGenre, maxResultsPerPage);

				Platform.runLater(() -> {
					centerBox.getChildren().clear();
				});

				int row = 0;
				int col = 0;

				for (Book book : bookdata) {
					ImageView coverPage = new ImageView(new Image(book.getCoverUrl()));
					coverPage.setFitHeight(150);
					coverPage.setPreserveRatio(true);

					coverPage.setOnMouseClicked(Event -> check(book, coverPage));

					coverPage.getStyleClass().add("cover-image");

					GridPane.setRowIndex(coverPage, row);
					GridPane.setColumnIndex(coverPage, col);

					Platform.runLater(() -> {
						centerBox.getChildren().add(coverPage);
						loadingSpinner.setVisible(false);
					});

					col++;

					if (col > 3) {
						col = 0;
						row++;
						if (row > 4) {
							break;
						}
					}
				}

				return null;
			}
		};
		Thread loadCustomGenre = new Thread(fetchBooksTask);
		loadCustomGenre.setDaemon(true);
		loadCustomGenre.start();
	}

	public void openUtility() {
		FXMLLoader loadUtility = new FXMLLoader(getClass().getResource("/utilityWindow.fxml"));
		utilityStage = new Stage();
		Parent utilRoot;
		try {
			utilRoot = loadUtility.load();
			Scene utilScene = new Scene(utilRoot);
			
			utilityWindowController utilController = loadUtility.getController();
			utilController.getLogoutButton().setOnMouseClicked(event->{
				Platform.exit();
			});
			utilityStage.setScene(utilScene);
			utilityStage.initStyle(StageStyle.UTILITY);
			utilityStage.setAlwaysOnTop(true);
			utilityStage.setX(1350);
			utilityStage.setY(150);
			utilityStage.setTitle("Menu");
			utilityStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			loginController.closeButton.setOnMouseClicked(Event -> {
				closedByButton = true;
				chooseToUpdateTheMainWindow();
			});
			loginStage.setOnHidden(event->{
				System.out.println(loginWindowController.UsernameFieldData);
				Tooltip avatarToolTip = new Tooltip(loginWindowController.UsernameFieldData);
				avatarToolTip.setShowDelay(Duration.millis(100));
				Tooltip.install(userAvatar, avatarToolTip);
				chooseToUpdateTheMainWindow();
				closedByButton = false; 
			});

			loginStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void chooseToUpdateTheMainWindow() {
		boolean gate = false;
		if(closedByButton)
		{
			loginStage.close();
			gate = true;
		}
		else if(closedByButton == false && gate == false)
		{
			updateTheStageShowStatus();
		}
	}

	public void updateTheStageShowStatus() {
		System.out.println("the stage was closed");
		loginButtonM.setVisible(false);
		userAvatar.setVisible(true);
	}

	public void check(Book book, ImageView clickedCoverPage) {
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

			if (stageIsShowing) {
				stage.close();
			}

			stage = new Stage();
			Parent newroot = reviewWindowLoad.load();
			Scene newScene = new Scene(newroot);

			reviewWindowController reviewController = reviewWindowLoad.getController();

			reviewController.setBookData(title, author, publicationDate, ratingsCount, rating, description, coverImage);
			stage.setScene(newScene);

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
				searchField, loadingSpinner, searchButton, coverPage, this, errorImage, errorLabel);

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

	public ImageView getUserAvatar() {
		return userAvatar;
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

	public ImageView getLoginButtonM() {
		return loginButtonM;
	}

	public AnchorPane getHeader() {
		return header;
	}

}