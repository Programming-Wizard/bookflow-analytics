package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class reviewWindowController implements Initializable{

	// Injecting FXML in the controller
	@FXML
	public ImageView closeButton;
	@FXML
	private ImageView Coverpage;
	@FXML
	public Label titleOfTheBook;
	@FXML
	public ImageView bg;
	@FXML
	private Label publishdate;
	@FXML
	private Label Rating;
	@FXML
	private Label ratingCount;
	@FXML
	private Label Authorsname;
	@FXML
	private Label description;
	@FXML
	private ImageView favButton;
	@FXML
	private ImageView errorImage;
	@FXML
	private Label errorLabel;
	private String title;
	private String author;
	private Image image;

	// getting the data from and therefore setting the required parameters
	public void setBookData(String title, String author, String publicationDate, int ratingsCount, double rating,
			String description, Image image) 
	{
		this.title = title;
		this.image = image; 
		this.author= author;
		
		titleOfTheBook.setText(title);
		Authorsname.setText(author);
		publishdate.setText(publicationDate);
		ratingCount.setText(String.valueOf(ratingsCount));
		Rating.setText(String.valueOf(rating));
		this.description.setText(description);
		Coverpage.setImage(image);
		
		double arcWidth = 20;
		double arcHeight= 20;
		Rectangle clip = new Rectangle(bg.getFitWidth(),bg.getFitHeight());
		clip.setArcWidth(arcWidth);
		clip.setArcHeight(arcHeight);
		bg.setClip(clip);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginWindowController.getUserId();
		errorImage.setVisible(false);
		errorLabel.setVisible(false);
		Tooltip tooltip = new Tooltip("Add To Favourites");
		tooltip.setShowDelay(Duration.millis(10));
		Tooltip.install(favButton, tooltip);
		favButton.setOnMouseClicked(event->{
			loginWindowController.getUserId();
			Image favButtonChange = new Image("/fav2.png");
			System.out.println(title);
			System.out.println(image);
			
			String usernameData = "root";
			String passwordData = "zephrus_02";

			String url = "jdbc:mysql://localhost:3306/LibraryUserData";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, usernameData, passwordData);
				String checkQuery = "SELECT * FROM favorite_books WHERE id = ? AND book_title = ?";
		        PreparedStatement checkStatement = con.prepareStatement(checkQuery);
		        checkStatement.setInt(1, loginWindowController.userId);
		        checkStatement.setString(2, title);
		        
		        ResultSet checkResult = checkStatement.executeQuery();
		        if(checkResult.next()) {
		        	System.out.println("this is already in your list");
		        	return;
		        }
				
				String insertingDataInTheTable= "INSERT INTO favorite_books (id, book_title, book_author) VALUES (? , ? , ?)";
				
				PreparedStatement preparedStatement = con.prepareStatement(insertingDataInTheTable);
				preparedStatement.setInt(1, loginWindowController.userId);
				preparedStatement.setString(2, title);
				preparedStatement.setString(3,author );
				
				preparedStatement.executeUpdate();
				
				preparedStatement.close();
				con.close();
				favButton.setImage(favButtonChange);
				Image image = new Image("greenTick.jpg");
				errorImage.setImage(image);
				displayingError("Successfully Added");
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				displayingError("please login or create account to mark this book in your favourites");
				System.out.println("please login or create account to mark this book in your favourites");
			}
			
		});
	}
	
	
	public void displayingError(String Message) {
		errorLabel.setVisible(true);
		errorImage.setVisible(true);
		errorLabel.setText(Message);
		errorLabel.setOpacity(1);
		errorImage.setOpacity(1);
		errorLabel.setStyle("-fx-background-color:white; -fx-border-radius:10;");

		Duration showDuration = Duration.seconds(3);
		Timeline showDurationTimeline = new Timeline(new KeyFrame(showDuration, event -> {
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), errorLabel);
			fadeOut.setToValue(0);
			fadeOut.setOnFinished(e -> {
				errorLabel.setVisible(false);
			});
			fadeOut.play();
		}));
		showDurationTimeline.stop();
		showDurationTimeline.play();
		Duration showDuration2 = Duration.seconds(3);
		Timeline showDurationTimeline2 = new Timeline(new KeyFrame(showDuration2, event -> {
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), errorImage);
			fadeOut.setToValue(0);
			fadeOut.setOnFinished(e -> {
				errorLabel.setVisible(false);
			});
			fadeOut.play();
		}));
		showDurationTimeline2.stop();
		showDurationTimeline2.play();

	}
	
	
}