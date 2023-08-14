package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
	public static boolean isOnHomeScreen = true;
	@Override
	public void start(Stage primaryStage) {
		try {
			Image icon = new Image("/logo.png"); 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/loadingScreen.fxml"));
			Parent root = loader.load();
			Scene loadingScene = new Scene(root);
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Stage loadingStage = new Stage();
			loadingStage.setTitle("Loading Books For You :");
			loadingStage.setResizable(false);
			loadingStage.getIcons().add(icon);
			loadingStage.setScene(loadingScene);
			loadingStage.show();

			loadMainWindowTask LoadMainWindowTask = new loadMainWindowTask();
			LoadMainWindowTask.setOnSucceeded(event1 -> {
				Parent mainRoot = LoadMainWindowTask.getValue();
				Scene mainScene = new Scene(mainRoot);
				primaryStage.setScene(mainScene);
				primaryStage.getIcons().add(icon);
				primaryStage.setTitle("Bookflow");
				primaryStage.show();

				mainWindowController MainWindow = LoadMainWindowTask.getController();

				loadingStage.close();
				MainWindow.getHomeButton().setOnMouseClicked(Event4 ->{
					if(isOnHomeScreen == false) {
						isOnHomeScreen = true;
						GoogleBooksApiClient apiClient = new GoogleBooksApiClient();
						String maxResultsPerPage = "20";
						GridPane centerBox = MainWindow.getCenterBox();
						ImageView coverPage = MainWindow.coverPage;

						centerBox.getChildren().clear();
						GoToHomeTask goToHomeTask = new GoToHomeTask(apiClient, maxResultsPerPage, centerBox, MainWindow.getLoadingSpinner(), coverPage, MainWindow);

						Thread th3 = new Thread(goToHomeTask);
						th3.setDaemon(true);
						th3.start();
					}
				});

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
				});

			});
			Thread th2 = new Thread(LoadMainWindowTask);
			th2.setDaemon(true);
			th2.start();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
