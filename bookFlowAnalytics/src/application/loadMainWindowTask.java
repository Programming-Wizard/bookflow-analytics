package application;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class loadMainWindowTask extends Task<Parent> {
	// making FXML loader instance so the controller can be returned
	private FXMLLoader mainLoader;

	@Override
	protected Parent call() throws Exception 
	{
		try 
		{
			// loading the main
			mainLoader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		// return the loaded main window
		return mainLoader.load();

	}

	// return the main window controller
	public mainWindowController getController() 
	{
		return mainLoader.getController();
	}
}