package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class LoadScreenController implements Initializable {
	@FXML
	private AnchorPane LoadingScreenPane;
	@FXML
	private 
	Label outputLabel;
	@FXML
	private MediaView background;
	
	public ProgressBar progressBar = new ProgressBar();
	private Thread progressThread;
	private Media media;
	private MediaPlayer mediaplayer;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		media = new Media(getClass().getResource("/animation-window.mp4").toExternalForm());
		mediaplayer = new MediaPlayer(media);
		background.setMediaPlayer(mediaplayer);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		progressBar.setPrefWidth(522);
		progressBar.setPrefHeight(18);
		progressBar.setLayoutX(38);
		progressBar.setLayoutY(331);
		progressBar.setScaleX(1);
		progressBar.setScaleY(1);
		progressBar.setScaleZ(1);
		LoadingScreenPane.getChildren().add(progressBar);

		progressThread = new Thread(() -> {
			for (double progress = 0; progress <= 1.0; progress += 0.1) {
				updateProgressBar(progress);
				try
				{
					Thread.sleep(1100);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		progressThread.setDaemon(true);
		progressThread.start();
	}

	private void updateProgressBar(double value) {
		Platform.runLater(() -> progressBar.setProgress(value));
	}
}
