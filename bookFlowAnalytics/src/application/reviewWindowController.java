package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class reviewWindowController implements Initializable {
	@FXML
	private ImageView Coverpage;
	@FXML
	public static Label titleOfTheBook;
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainWindowController getFromMainWindow = new mainWindowController();
		titleOfTheBook.setText(getFromMainWindow.getTitle());
		Authorsname.setText(getFromMainWindow.getAuthor());
		publishdate.setText(getFromMainWindow.getPublicationDate());
		ratingCount.setText(String.valueOf(getFromMainWindow.getRatingsCount()));
		Rating.setText(String.valueOf(getFromMainWindow.getRating()));
		description.setText(getFromMainWindow.getDescription());
		// Set the clicked cover image
//		Coverpage.setImage(getFromMainWindow.getClickedBook().getImage());
	}
	public void showup()
	{

		
	}
	
}


