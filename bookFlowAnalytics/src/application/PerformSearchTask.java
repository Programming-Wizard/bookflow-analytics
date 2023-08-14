package application;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PerformSearchTask extends Task<Void> {

    private final GoogleBooksApiClient apiClient;
    private final String maxResultsPerPage;
    private final GridPane centerBox;
    private final ProgressIndicator loadingSpinner;
    private final Button searchButton;
    private final TextField searchField;
    private ImageView coverPage;
    private final mainWindowController mainWindowController;
    int totalbooks = 0;
    public PerformSearchTask(GoogleBooksApiClient apiClient,
    						String maxResultsPerPage,
                             GridPane centerBox,TextField searchField,
                             ProgressIndicator loadingSpinner, Button searchButton,
                             ImageView coverPage, mainWindowController mainWindowController) {
        this.apiClient = apiClient;
        this.maxResultsPerPage = maxResultsPerPage;
        this.centerBox = centerBox;
        this.loadingSpinner = loadingSpinner;
        this.searchButton = searchButton;
        this.searchField = searchField;
        this.coverPage = coverPage;
        this.mainWindowController = mainWindowController;	
    }

    @Override
    protected Void call() throws Exception {
    	try {
    	loadingSpinner.setVisible(true);
		searchButton.setDisable(true);
		String query = searchField.getText();
		if(query == "" || query == null)
		{
			loadingSpinner.setVisible(false);
			searchButton.setDisable(false);
			return null;
		}
		
        List<Book> bookdata = apiClient.fetchBooksData(query, maxResultsPerPage);
        Platform.runLater(() ->{
        	centerBox.getChildren().clear();
        });
        	
        int row = 0;
        int col = 0;

        

        for (Book book : bookdata) {
            if (book != null) {
                totalbooks++;
                coverPage = new ImageView(new Image(book.getCoverUrl()));
                coverPage.setFitHeight(150);
                coverPage.setPreserveRatio(true);
                coverPage.getStyleClass().add("cover-image");
                coverPage.setOnMouseClicked(Event -> mainWindowController.check(book, coverPage));
                
                GridPane.setRowIndex(coverPage, row);
                GridPane.setColumnIndex(coverPage, col);
                
                Platform.runLater(() ->{
                	centerBox.getChildren().add(coverPage);
                    if (totalbooks <= 20) {
                        loadingSpinner.setVisible(false);
                        searchButton.setDisable(false);
                }
                });
                
                col++;
                if(col > 3){
                	col = 0;
                	row++;
                	if(row > 4)
                	{
                		break;
                	}
                }
            }


        }
    	}catch (java.lang.IllegalArgumentException e) {
    		System.out.println("insufficient data available to be fetched");
		}
    	
    	loadingSpinner.setVisible(false);
    	searchButton.setDisable(false);

        return null; // The result type of this Task is Void
    }
}
