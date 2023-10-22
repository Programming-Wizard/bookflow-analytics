package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class reviewWindowController {

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

	// getting the data from and therefore setting the required parameters
	public void setBookData(String title, String author, String publicationDate, int ratingsCount, double rating,
			String description, Image image) 
	{
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
}
