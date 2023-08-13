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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/loadingScreen.fxml"));
			Parent root = loader.load();
			Scene loadingScene = new Scene(root);
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Stage loadingStage = new Stage();
			loadingStage.setScene(loadingScene);
			loadingStage.show();
			System.out.println("showed the loading screen");

			loadMainWindowTask LoadMainWindowTask = new loadMainWindowTask();
			System.out.println("loaded the task initiated as well");
			System.out.println("checking if it succeded in it ");
			LoadMainWindowTask.setOnSucceeded(event1 -> {
				System.out.println("it succeded");
				Parent mainRoot = LoadMainWindowTask.getValue();
				Scene mainScene = new Scene(mainRoot);
				primaryStage.setScene(mainScene);
				System.out.println("stage is about to be showed");
				primaryStage.setTitle("Bookflow");
				primaryStage.show();

				mainWindowController MainWindow = LoadMainWindowTask.getController();
				System.out.println("got the the controller");

				loadingStage.close();
				System.out.println("closed the loading screen");

				
				MainWindow.graphSceneButton.setOnMouseClicked(Event2 ->{
					try {
						FXMLLoader graphSceneLoad = new FXMLLoader(getClass().getResource("/bookflowAnalytics.fxml"));
						Parent newRoot = graphSceneLoad.load();
						Scene graphScene = new Scene(newRoot, screenBounds.getWidth(),(screenBounds.getHeight() - 27));
						primaryStage.setScene(graphScene);
						primaryStage.setResizable(false);
						bookFlowAnalyticsController GraphSceneController = graphSceneLoad.getController();

						GraphSceneController.backToMainWindow.setOnMouseClicked(Event3 ->{
							try {
								primaryStage.setScene(mainScene);
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
					System.out.println("thread is about to start");
				});

			});
			Thread th2 = new Thread(LoadMainWindowTask);
			th2.setDaemon(true);
			th2.start();
			System.out.println("process ended");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
