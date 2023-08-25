package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	private Media media;
	private MediaPlayer mediaplayer;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		media = new Media(getClass().getResource("/animation-window.mp4").toExternalForm());
		mediaplayer = new MediaPlayer(media);
		background.setMediaPlayer(mediaplayer);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		
		errorImage.setVisible(false);
		errorLabel.setVisible(false);
	}
	
	
	
	
}
