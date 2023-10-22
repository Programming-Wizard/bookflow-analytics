package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class loginWindowController implements Initializable{
	@FXML
	public ImageView closeButton;
	@FXML
	private Label errorLabel;
	@FXML
	private ImageView errorImage;
	@FXML
	private Button loginBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		errorImage.setVisible(false);
		errorLabel.setVisible(false);
	}
	
	public void checkLoginRequest()
	{
		pythonProcessTask task1 = new pythonProcessTask();
		Thread th = new Thread(task1);
		th.setDaemon(true);
		th.start();
	}
	
	
	
	
}
