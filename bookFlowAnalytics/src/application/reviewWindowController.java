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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class reviewWindowController{
	@FXML
	private ImageView Coverpage;
	@FXML
	public Label titleOfTheBook;
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

	public void setBookData(String title, String author, String publicationDate, int ratingsCount, double rating,String description, Image image) {
		// TODO Auto-generated method stub
      titleOfTheBook.setText(title);
      Authorsname.setText(author);
      publishdate.setText(publicationDate);
      ratingCount.setText(String.valueOf(ratingsCount));
      Rating.setText(String.valueOf(rating));
      this.description.setText(description);
      Coverpage.setImage(image);
	}
	
}






