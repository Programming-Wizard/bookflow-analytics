package application;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class loadMainWindowTask extends Task<Parent>{
	private FXMLLoader mainLoader;
	@Override
	protected Parent call() throws Exception {
		System.out.println("this is call method");
		try {
			System.out.println("try block is executing");
			mainLoader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));
			System.out.println("its done loading");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mainLoader.load();

	}
    public mainWindowController getController() {
    	System.out.println("returning the controller");
        return mainLoader.getController();
    }
	
}
