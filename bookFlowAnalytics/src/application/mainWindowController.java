package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class mainWindowController implements Initializable {

	@FXML
	private GridPane centerBox;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	public ImageView graphSceneButton;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apiClient = new GoogleBooksApiClient();

		String query = "fiction";

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

				Label titleLabel = new Label(book.getTitle());
				titleLabel.setWrapText(true);
				titleLabel.setMaxWidth(150); // Set a maximum width for the label


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
	private void check(Book book, ImageView clickedCoverPage)
	{
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.publicationDate = book.getPubDate();
		this.ratingsCount = book.getRatingsCount();
		this.rating = book.getRating();
		this.description = book.getDescription();
		this.clickedBook = clickedCoverPage;
		
//		System.out.println(Booktitle);
//		System.out.println(author);
//		System.out.println(publicationDate);
//		System.out.println(ratingsCount);
//		System.out.println(rating);
//		System.out.println(description);
//		System.out.println(clickedBook);
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
