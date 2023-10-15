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
	private MediaView background;
	@FXML
	private Button loginBtn;
	private Media media;
	private MediaPlayer mediaplayer;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		media = new Media(getClass().getResource("/background2.mp4").toExternalForm());
		mediaplayer = new MediaPlayer(media);
		background.setMediaPlayer(mediaplayer);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
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
