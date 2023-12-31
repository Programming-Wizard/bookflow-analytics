package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.python.core.PyByteArray.bytearray_partition_exposer;
import org.python.icu.text.CaseMap.Title;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class favWindowController implements Initializable {
	
    @FXML
    public ImageView closeButton;
    @FXML
    public VBox container;
    Font customFont = Font.loadFont(getClass().getResourceAsStream("/BoogieBoysRegular-L36y3.otf"), 30);
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
        	String usernameData = "root";
			String passwordData = "zephrus_02";

			String url = "jdbc:mysql://localhost:3306/LibraryUserData";
			Connection connection = DriverManager.getConnection(url,usernameData,passwordData);
            String query = "SELECT book_title, book_author FROM favorite_books WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, loginWindowController.userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bookTitle = resultSet.getString("book_title");
                String bookAuthor = resultSet.getString("book_author");

                Label titleLabel = new Label();
                Label authorLabel = new Label();
                titleLabel.setText("Book Title : " + bookTitle + "\n");
                authorLabel.setText("Author Name : " + bookAuthor + "\n\n");
                
                titleLabel.setWrapText(true);
                titleLabel.setStyle("-fx-background-color: #f3f3f3; -fx-background-radius: 5px; -fx-padding: 10px; -fx-border-color: #ddd; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-wrap-text: true;");
                titleLabel.setFont(customFont);

                // Add labels to the container (VBox)
                container.getChildren().addAll(authorLabel, titleLabel);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
