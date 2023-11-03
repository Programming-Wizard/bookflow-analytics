package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class utilityWindowController implements Initializable{
	@FXML 
	private Label tab;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FXMLLoader loadFavWindow = new FXMLLoader(getClass().getResource("/favWindow.fxml"));
		Stage favWindow = new  Stage();
		tab.setOnMouseClicked(event->{

			Parent favWindowRoot;
			mainWindowController.utilityStage.close();
			try {
				favWindowRoot = loadFavWindow.load();
				favWindowController favwindowController = loadFavWindow.getController();
				Scene favWindowScene = new Scene(favWindowRoot,Color.TRANSPARENT);
				favWindow.setScene(favWindowScene);
				favWindow.initStyle(StageStyle.TRANSPARENT);
				favWindow.setX(440);
				favWindow.setY(180);
				favWindow.show();
				favwindowController.closeButton.setOnMouseClicked(Event->{
					favWindow.close();
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}



}
