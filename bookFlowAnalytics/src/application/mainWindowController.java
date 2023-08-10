package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class mainWindowController implements Initializable {

	@FXML
	private GridPane centerBox;

	private GoogleBooksApiClient apiClient;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		apiClient = new GoogleBooksApiClient();

		String query = "fiction";
		int startIndex = 0;
		int maxResultsPerPage = 20;

		Scene scene = centerBox.getScene();

		List<Book> bookdata = apiClient.fetchBooksData(query);

		int row = 0;
		int col = 0;

		for (Book book : bookdata) {
			StackPane stackPane = new StackPane();
			ImageView coverPage = new ImageView(new Image(book.getCoverUrl()));
			coverPage.setFitHeight(150);
			coverPage.setPreserveRatio(true);

			coverPage.setOnMouseClicked(event -> {
				System.out.println("clicked on book : " + book.getTitle());
			});

			Label titleLabel = new Label(book.getTitle());
			titleLabel.setWrapText(true);
			titleLabel.setMaxWidth(150); // Set a maximum width for the label

			stackPane.getChildren().addAll(coverPage);

			GridPane.setRowIndex(stackPane, row);
			GridPane.setColumnIndex(stackPane, col);

			centerBox.getChildren().add(stackPane);

			col++;
			if (col > 3) { // Display 3 books per row
				col = 0;
				row++;

				if (row > 4) {
					break;
				}
			}

		}

	}

	public void handleResize() {
		Stage stage = (Stage) centerBox.getScene().getWindow();
		Scene scene = centerBox.getScene();
		double windowWidth = scene.getWidth();
		double windowHeight = scene.getHeight();

		double coverWidth = windowWidth / 4;
		double coverHeight = windowWidth * (3.0 / 2.0);

		for (Node node : centerBox.getChildren()) {
			if (node instanceof StackPane) {
				StackPane stackPane = (StackPane) node;
				ImageView coverPage = (ImageView) stackPane.getChildren().get(0); // Assuming ImageView is the first
																					// child

				coverPage.setFitWidth(coverWidth);
				coverPage.setFitHeight(coverHeight);
			}
		}
	}
}
