package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class loginWindowController implements Initializable{
	@FXML
	public ImageView closeButton;
	@FXML
	private Label errorLabel;
	@FXML
	private ImageView errorImage;
	@FXML
	private Button loginBtn;
	@FXML
	private TextField username;
	@FXML
	private TextField userEmail;
	@FXML
	private TextField signInCode;
	@FXML
	private PasswordField password;
	public static String enteredText;
	pythonProcessTask task1 = new pythonProcessTask();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		errorImage.setVisible(false);
		errorLabel.setVisible(false);
		task1.setOnSucceeded(event->{
			System.out.println("task has been executed and completed");
		});
		task1.setOnFailed(event->{
			System.out.println("task was executed but had an error");
		});
	}
	
	public void checkLoginRequest()
	{
		enteredText = userEmail.getText();
		Thread th = new Thread(task1);
		th.setDaemon(true);
		th.start();
	}
	
	
	
}
