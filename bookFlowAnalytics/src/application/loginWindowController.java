package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class loginWindowController implements Initializable {
	@FXML
	public ImageView closeButton;
	@FXML
	private Label errorLabel;
	@FXML
	private ImageView errorImage;
	@FXML
	private ImageView loginBtn;
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
	private String usernameData;
	private String passwordData;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginBtn.setDisable(true);
		errorImage.setVisible(false);
		errorLabel.setVisible(false);
		task1.setOnSucceeded(event -> {
			System.out.println("task has been executed and completed");
		});
		task1.setOnFailed(event -> {
			System.out.println("task was executed but had an error");
		});
	}

	public void checkLoginRequest() {
		loginBtn.setDisable(false);
		enteredText = userEmail.getText();
		Thread th = new Thread(task1);
		th.setDaemon(true);
		th.start();
	}

	public void signInButtonTask() {
		loginRequest();
		mainWindowController.loginStage.close();
	}

	public void loginRequest() {
		errorImage.setVisible(false);
		errorLabel.setVisible(false);
//		this.usernameData = username.getText();
//		this.passwordData = password.getText();
		usernameData = "root";
		passwordData = "zephrus_02";
		
		String url = "jdbc:mysql://localhost:3306/LibraryUserData";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, usernameData, passwordData);
					
				con.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
			}
	}
}
