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
	// injecting FXML in the controller
	@FXML
	private AnchorPane LoadingScreenPane;
	@FXML
	private Label outputLabel;
	@FXML
	private MediaView background;

	public ProgressBar progressBar = new ProgressBar();
	private Thread progressThread;
	private Media media;
	private static MediaPlayer mediaplayer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		setting a video background for a better visual
		media = new Media(getClass().getResource("/animation-window1.mp4").toExternalForm());
		mediaplayer = new MediaPlayer(media);
		background.setMediaPlayer(mediaplayer);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setMute(true);
		mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);

//		creating a progress bar programmatically instead of injecting it from FXML as doing that, a NullPointerException was encountered
		progressBar.setPrefWidth(522);
		progressBar.setPrefHeight(18);
		progressBar.setLayoutX(38);
		progressBar.setLayoutY(331);
		progressBar.setScaleX(1);
		progressBar.setScaleY(1);
		progressBar.setScaleZ(1);

//		adding the created progress bar to the scene
		LoadingScreenPane.getChildren().add(progressBar);

//		creating a thread to show progress in the thread
		progressThread = new Thread(() -> {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (double progress = 0; progress <= 1.0; progress += 0.1) {
//				calling the method and passing the updated the value to the method every time the loop is executed
				updateProgressBar(progress);
				try {
					Thread.sleep(1100);
				} catch (InterruptedException e) {
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

	public static MediaPlayer getMediaplayer() {
		return mediaplayer;
	}
}