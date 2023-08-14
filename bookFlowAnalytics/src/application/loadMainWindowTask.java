package application;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class loadMainWindowTask extends Task<Parent>{
	private FXMLLoader mainLoader;
	@Override
	protected Parent call() throws Exception {
		try {
			mainLoader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mainLoader.load();

	}
    public mainWindowController getController() {
        return mainLoader.getController();
    }
	
}
