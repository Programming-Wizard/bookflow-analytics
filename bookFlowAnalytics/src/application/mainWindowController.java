package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class mainWindowController implements Initializable {

	@FXML
	private GridPane centerBox;
	@FXML
	private ScrollPane scrollPane;

	private GoogleBooksApiClient apiClient;
	private boolean testingmode = false;

	private int startIndex = 0;
	private int maxResultsPerPage = 20;
	private List<Timeline> hoverAnimations  = new ArrayList<>();
	private List<Timeline> notHoveredAnimations = new ArrayList<>();
	private ImageView hoveredImageView;
	public ImageView coverPage;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apiClient = new GoogleBooksApiClient();

		String query = "fiction";

		if (testingmode == false) 
		{
			List<Book> bookdata = apiClient.fetchBooksData(query);

			int row = 0;
			int col = 0;

			for (Book book : bookdata) 
			{
				StackPane stackPane = new StackPane();
				coverPage = new ImageView(new Image(book.getCoverUrl()));
				coverPage.setFitHeight(150);
				coverPage.setPreserveRatio(true);
				createHoverAnimation(coverPage);
				createNotHoverAnimation(coverPage);

				coverPage.setOnMouseEntered(event -> MouseEntered(coverPage));
				coverPage.setOnMouseExited(event -> MouseLeft(coverPage));

				Label titleLabel = new Label(book.getTitle());
				titleLabel.setWrapText(true);
				titleLabel.setMaxWidth(150); // Set a maximum width for the label

				stackPane.getChildren().addAll(coverPage);

				GridPane.setRowIndex(stackPane, row);
				GridPane.setColumnIndex(stackPane, col);

				centerBox.getChildren().add(stackPane);

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


	private void createHoverAnimation(ImageView coverPage)
	{
		Timeline hoverAnimation = new Timeline();
		KeyFrame keyframe1 = new KeyFrame(javafx.util.Duration.ZERO, new KeyValue(coverPage.translateYProperty(),0));
		KeyFrame keyframe2 = new KeyFrame(javafx.util.Duration.millis(50), new KeyValue(coverPage.translateYProperty(),-5));
		hoverAnimation.getKeyFrames().addAll(keyframe1,keyframe2);
	}
	public void MouseEntered(ImageView coverPage)
	{
	       int index = centerBox.getChildren().indexOf(coverPage.getParent());
	        if (index >= 0 && index < hoverAnimations.size()) {
	            hoverAnimations.get(index).play();
	        }
		}

	private void createNotHoverAnimation(ImageView coverPage)
	{
		Timeline notHoveredAnimation = new Timeline();
		KeyFrame keyframe1 = new KeyFrame(javafx.util.Duration.ZERO, new KeyValue(coverPage.translateYProperty(),0));
		KeyFrame keyframe2 = new KeyFrame(javafx.util.Duration.millis(50), new KeyValue(coverPage.translateYProperty(),5));
		notHoveredAnimation.getKeyFrames().addAll(keyframe1,keyframe2);
	}
	public void MouseLeft(ImageView coverPage)
	{
        int index = centerBox.getChildren().indexOf(coverPage.getParent());
        if (index >= 0 && index < notHoveredAnimations.size()) {
            notHoveredAnimations.get(index).play();
        }
	}

	public void handleResize() 
	{
		centerBox.setPrefWidth(658);
		centerBox.setPrefHeight(780);
	}

	public void getScrollbarPosition() 
	{
		double value = scrollPane.getVvalue();

		if (value == 1.0) {
			centerBox.addRow(10);
			System.out.println(centerBox.getPrefHeight());

			System.out.println(centerBox.getRowCount());
			System.out.println(centerBox.getColumnCount());
		}
	}

}
