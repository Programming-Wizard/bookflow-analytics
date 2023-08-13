package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));
			Parent root = loader.load();
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Scene scene = new Scene(root, screenBounds.getWidth(),(screenBounds.getHeight() - 27));
			mainWindowController MainWindow = loader.getController();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("/logo.png"); 
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("BookFlow");
			primaryStage.setResizable(false);
			
			primaryStage.setScene(scene);

			MainWindow.graphSceneButton.setOnMouseClicked(Event ->{
				try {
					 	FXMLLoader graphSceneLoad = new FXMLLoader(getClass().getResource("/bookflowAnalytics.fxml"));
	                    Parent newRoot = graphSceneLoad.load();
	                    Scene graphScene = new Scene(newRoot, screenBounds.getWidth(),(screenBounds.getHeight() - 27));
	                    primaryStage.setScene(graphScene);
	                    primaryStage.setResizable(false);
	                    bookFlowAnalyticsController GraphSceneController = graphSceneLoad.getController();

	                    GraphSceneController.backToMainWindow.setOnMouseClicked(Event2 ->{
	                    	try {
	                    		primaryStage.setScene(scene);
	                    		primaryStage.show();
	                    	}catch (Exception e) {
								e.printStackTrace();
							}
	                    });

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
