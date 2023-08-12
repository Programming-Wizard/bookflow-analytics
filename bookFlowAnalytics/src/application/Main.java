package application;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			mainWindowController MainWindow = loader.getController();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("/logo.png"); 
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("BookFlow");
//			primaryStage.setFullScreen(true);
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);

			primaryStage.setScene(scene);

			scene.setOnKeyPressed(Event ->{
				if(Event.getCode() == KeyCode.ESCAPE)
				{
					MainWindow.handleResize();
				}
			});
			MainWindow.graphSceneButton.setOnMouseClicked(Event ->{
				try {
					 	FXMLLoader graphSceneLoad = new FXMLLoader(getClass().getResource("/bookflowAnalytics.fxml"));
	                    Parent newRoot = graphSceneLoad.load();
	                    Scene graphScene = new Scene(newRoot);
	                    primaryStage.setScene(graphScene);
	                    primaryStage.setResizable(true);
	                    primaryStage.show();

				} catch (Exception e) 
				{
					e.printStackTrace();
				}

			});
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
