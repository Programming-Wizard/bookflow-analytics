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
	
//	passing the isOnHomeScreen publicly to be able to change it value from other classes easily rather than making it private and setting getter for it
	public static boolean isOnHomeScreen = true;

	@Override
	public void start(Stage primaryStage) {
		
		try 
		{
//			setting icon, getting screen size and loading, loading screen
			Image icon = new Image("/logo.png");
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/loadingScreen.fxml"));
			Parent root = loader.load();
			
			Scene loadingScene = new Scene(root);
			Stage loadingStage = new Stage();
			
//			setting up loading screen and showing it in a separate stage for easier closing and opening of the main window
			loadingStage.setTitle("Loading Books For You :");
			loadingStage.setResizable(false);
			loadingStage.getIcons().add(icon);
			loadingStage.setScene(loadingScene);
			loadingStage.show();

//			running a task in the background and i.e. to load the main window
			loadMainWindowTask LoadMainWindowTask = new loadMainWindowTask();
			
//			setting and showing the main window once the task is done loading the main window
			LoadMainWindowTask.setOnSucceeded(event1 -> 
			{
				Parent mainRoot = LoadMainWindowTask.getValue();
				Scene mainScene = new Scene(mainRoot);
				
				primaryStage.setScene(mainScene);
				primaryStage.getIcons().add(icon);
				primaryStage.setTitle("Bookflow");
				primaryStage.show();
				
//				getting controller from the task file as the the main window is being loaded in the background task
				mainWindowController MainWindow = LoadMainWindowTask.getController();

				loadingStage.close();
				
//				adding event listener to the home button image
				MainWindow.getHomeButton().setOnMouseClicked(Event4 -> 
				{
					if (isOnHomeScreen == false) 
					{
						isOnHomeScreen = true;
						
//						fetching the data with the same query which we we got selected while loading the main window and showing it again
						GoogleBooksApiClient apiClient = new GoogleBooksApiClient();
						String maxResultsPerPage = "20";
						GridPane centerBox = MainWindow.getCenterBox();
						ImageView coverPage = MainWindow.coverPage;

//						clearing the grid so the previous cover pages (if any) get cleared and you get a new fresh grid which was on the home screen.
						centerBox.getChildren().clear();
						
//						to fetch and update this data as well a new background task has been created in order to keep the application responsive
						GoToHomeTask goToHomeTask = new GoToHomeTask(apiClient, maxResultsPerPage, centerBox,
								MainWindow.getLoadingSpinner(), coverPage, MainWindow);

//						running the task by making a thread
						Thread th3 = new Thread(goToHomeTask);
						th3.setDaemon(true);
						th3.start();
					}
				});

//				event listener for the button which on clicked shows the user a graph of books with new genre everytime a click is listened
				MainWindow.graphSceneButton.setOnMouseClicked(Event2 -> 
				{
					try 
					{
						FXMLLoader graphSceneLoad = new FXMLLoader(getClass().getResource("/bookflowAnalytics.fxml"));
						Parent newRoot = graphSceneLoad.load();
						
						Scene graphScene = new Scene(newRoot, screenBounds.getWidth(), (screenBounds.getHeight() - 27));
						primaryStage.setScene(graphScene);
						primaryStage.setResizable(false);
						
						bookFlowAnalyticsController GraphSceneController = graphSceneLoad.getController();

//						event listener for back button which is on the graph screen which on click just switches the scene back to the main window
						GraphSceneController.backToMainWindow.setOnMouseClicked(Event3 -> 
						{
							try 
							{
								primaryStage.setScene(mainScene);
								primaryStage.show();
							} catch (Exception e) 
							{
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
			
//			running the task by making a thread
			Thread th2 = new Thread(LoadMainWindowTask);
			th2.setDaemon(true);
			th2.start();

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}