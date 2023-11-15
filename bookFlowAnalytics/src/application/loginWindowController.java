package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class loginWindowController implements Initializable {
	@FXML
	public ImageView closeButton;
	@FXML
	private Label errorLabel;
	@FXML
	private ImageView errorImage;
	@FXML
	private ImageView SignBtn;
	@FXML
	private TextField username;
	@FXML
	private TextField userEmail;
	@FXML
	private TextField signInCode;
	@FXML
	private PasswordField password;
	@FXML
	private Button verificationButton;
	@FXML
	private ProgressBar loadingComponent;
	@FXML
	private Label Login;
	@FXML
	private Text emailLabel;
	@FXML
	private Text signInLabel;
	@FXML
	private Text passwordLabel;
	@FXML
	private Text usernameLabel;
	@FXML
	private AnchorPane signUpWindowCanvas;
	private Timeline timeline;
	public static String enteredText;
	pythonProcessTask task1 = new pythonProcessTask();
	private String usernameData;
	private String passwordData;
	public static String UsernameFieldData;
	public static int userId;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Tooltip tooltip = new Tooltip("Before Clicking This Make Sure To Enter Correct And Valid Or An Available Email");
		tooltip.setShowDelay(Duration.millis(100));
		Tooltip.install(verificationButton, tooltip);
		loadingComponent.setVisible(false);
		SignBtn.setDisable(true);
		errorImage.setVisible(false);
		errorLabel.setVisible(false);
		signInCode.setDisable(true);
		task1.setOnSucceeded(event -> {
			System.out.println("task has been executed and completed");
		});
		task1.setOnFailed(event -> {
			System.out.println("task was executed but had an error");
		});
		password.setOnAction(event->loginInstruction());
	}
	
	public void checkLoginRequest() {
		if (userEmail.getText().strip().isEmpty()) {
			displayingError("Enter your email to verify");
			return;
		}
		userEmail.setEditable(false);
		signInCode.setDisable(false);
		loadingComponent.setVisible(true);
		SignBtn.setDisable(false);
		verificationButton.setVisible(false);
		enteredText = userEmail.getText();
		Thread th = new Thread(task1);
		th.start();

//		updating the status of email being sent
		timeline = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (!th.isAlive()) {
					System.out.println("the thread has died");
					loadingComponent.setVisible(false);
					verificationButton.setVisible(false);
					timeline.stop();
				}
			}

		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public void signInButtonTask() {
		String sentCode = pythonProcessTask.code;
		String emailContent = userEmail.getText().strip();
		String passwordContent = password.getText().strip();
		String verificationCode = signInCode.getText().strip();
		String usernameContent = username.getText().strip();

		if (emailContent.equals("") || passwordContent.equals("") || verificationCode.equals("")
				|| usernameContent.equals("")) {
			if (emailContent.isEmpty()) {
				displayingError("Enter Email");
			} else if (passwordContent.isEmpty()) {
				displayingError("Enter password");
			} else if (verificationCode.isEmpty()) {
				displayingError("Enter code");
			} else if (usernameContent.isEmpty()) {
				displayingError("Enter username");
			}
			return;
		}
		if (verificationCode.equals(sentCode)) {
//			checking if the entered code matches
			System.out.println("they match");
		} else {
			displayingError("Wrong code entered");
			System.out.println("they dont match");
			return;
		}
		InsertUser();
	}

	public void InsertUser() {

		String usernameContent = username.getText().strip();
		String emailContent = userEmail.getText().strip();
		errorImage.setVisible(false);
		errorLabel.setVisible(false);
		usernameData = "root";
		passwordData = "zephrus_02";

		String url = "jdbc:mysql://localhost:3306/LibraryUserData";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, usernameData, passwordData);
//				checking if the user already exists or not
			String SqlQueryFindUser = "SELECT COUNT(*) FROM UserDetails WHERE username = ? OR email = ?";
			PreparedStatement checkStatement = con.prepareStatement(SqlQueryFindUser);
			checkStatement.setString(1, usernameContent);
			checkStatement.setString(2, emailContent);
			ResultSet checkSet = checkStatement.executeQuery();

			checkSet.next();

			if (checkSet.getInt(1) > 0) {
				displayingError("User exists");
				userEmail.setEditable(true);
				signInCode.clear();
				
				System.out.println("user exists");
				
				return;
			} else {
//				the user was not found and can be added in the database and further can be processed from here
				System.out.println("no user found with this credentials can execute further");
			}

			String SqlQueryInsertUser = "INSERT INTO UserDetails (username, email, password) VALUES (? , ?, ?)";
			PreparedStatement preparedStatement = con.prepareStatement(SqlQueryInsertUser);
			preparedStatement.setString(1, username.getText().strip());
			preparedStatement.setString(2, userEmail.getText().strip());
			preparedStatement.setString(3, password.getText().strip());

			int checkStatusOfTheQuery = preparedStatement.executeUpdate();

			if (checkStatusOfTheQuery > 0) {
				System.out.println("the user has been added to the database ");
				displayingError("User Account has been created");
				UsernameFieldData = username.getText();
				getUserId();
				mainWindowController.loginStage.close();
			} else {
				displayingError("Some unknown error occured try again");
				System.out.println("it didnt worked out the user is not added ");
			}
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loginInstruction() {
		String usernameContent = username.getText().strip();
		String passwordContent = password.getText().strip();
		if (usernameContent.isEmpty()) {
			displayingError("Enter Username or Email");
		} else if (passwordContent.isEmpty()) {
			displayingError("Enter Password");
		} else {
			usernameData = "root";
			passwordData = "zephrus_02";

			String url = "jdbc:mysql://localhost:3306/LibraryUserData";
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, usernameData, passwordData);
				String SqlQueryFindUser = "SELECT password FROM UserDetails WHERE username = ?";
				PreparedStatement preparedStatement = con.prepareStatement(SqlQueryFindUser);
				preparedStatement.setString(1, usernameContent);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					String storedPassword = resultSet.getString("password");
//					checking if the user is found and if can be logged in 
					if (passwordContent.equals(storedPassword)) {
						System.out.println("the user has account and can be logged in");
						displayingError("User Logged In");
						UsernameFieldData = username.getText();
						getUserId();
						mainWindowController.loginStage.close();
					} else {
						displayingError("incorrect password");
					}
				} else {
					String NewSqlQueryFindUser = "SELECT password FROM UserDetails WHERE email = ?";
					PreparedStatement preparedStatement1 = con.prepareStatement(NewSqlQueryFindUser);
					preparedStatement1.setString(1, usernameContent);
					ResultSet resultSet1 = preparedStatement1.executeQuery();
					if (resultSet1.next()) {
						String storedPassword = resultSet1.getString("password");
						if (passwordContent.equals(storedPassword)) {
							System.out.println("authenticated");
							mainWindowController.loginStage.close();
						} else {
							displayingError("incorrect password");
						}
					} else {
						displayingError("user not found");
					}
				}
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
				displayingError("This is SQL error");
			}
		}

	}
	private void changeBackToSignIn() {
		signUpWindowCanvas.setStyle("-fx-background-color: rgba(31, 184, 255,0.5);");
		signInCode.setVisible(true);
		signInCode.setDisable(true);

		userEmail.setVisible(true);
		userEmail.setDisable(false);
		emailLabel.setVisible(true);
		emailLabel.setDisable(false);

		signInLabel.setVisible(true);
		signInLabel.setDisable(false);

		verificationButton.setVisible(true);
		verificationButton.setDisable(false);

		passwordLabel.setTranslateY(passwordLabel.getTranslateY() + 90);
		password.setTranslateY(passwordLabel.getTranslateY());
		password.setPromptText("Enter Your Password");

		username.setPromptText("Enter Username");
		usernameLabel.setText("Username :");
		usernameLabel.setTranslateX(usernameLabel.getTranslateX() + 50);

		SignBtn.setDisable(true);
		SignBtn.setOnMousePressed(event -> signInButtonTask());
		Image image = new Image("/sign_in_2.png");
		SignBtn.setImage(image);
		Login.setOnMouseClicked(event -> LoginUser());
		Login.setText("Already Have An Account ? Log in.");
	}

	public void LoginUser() {
//		overriding the whole sign in window and making it into a login window 
		signUpWindowCanvas.setStyle("-fx-background-color: rgba(0, 252, 255, 0.5);");
		signInCode.setVisible(false);
		signInCode.isDisable();

		userEmail.setVisible(false);
		userEmail.isDisable();
		emailLabel.setVisible(false);
		emailLabel.isDisable();

		signInLabel.setVisible(false);
		signInLabel.isDisable();

		verificationButton.setVisible(false);
		verificationButton.isDisable();

		passwordLabel.setTranslateY(passwordLabel.getTranslateY() - 90);
		password.setTranslateY(passwordLabel.getTranslateY());
		password.setPromptText("Enter Your Password");

		username.setPromptText("Enter Username");
		usernameLabel.setText("Username :");
		Login.setOnMouseClicked(event -> changeBackToSignIn());
		Login.setText("Don't Have An Account ? Sign in.");
		Image image = new Image("/login_Button.png");
		SignBtn.setImage(image);

		SignBtn.setDisable(false);
		SignBtn.setOnMousePressed(event -> {
			loginInstruction();
		});
	}
	public void displayingError(String Message) {
		errorLabel.setVisible(true);
		errorImage.setVisible(true);
		errorLabel.setText(Message);
		errorLabel.setOpacity(1);
		errorImage.setOpacity(1);
		errorLabel.setStyle("-fx-background-color:white; -fx-border-radius:10;");

		Duration showDuration = Duration.seconds(3);
		Timeline showDurationTimeline = new Timeline(new KeyFrame(showDuration, event -> {
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), errorLabel);
			fadeOut.setToValue(0);
			fadeOut.setOnFinished(e -> {
				errorLabel.setVisible(false);
			});
			fadeOut.play();
		}));
		showDurationTimeline.stop();
		showDurationTimeline.play();
		Duration showDuration2 = Duration.seconds(3);
		Timeline showDurationTimeline2 = new Timeline(new KeyFrame(showDuration2, event -> {
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), errorImage);
			fadeOut.setToValue(0);
			fadeOut.setOnFinished(e -> {
				errorLabel.setVisible(false);
			});
			fadeOut.play();
		}));
		showDurationTimeline2.stop();
		showDurationTimeline2.play();

	}
	
	public static 	void getUserId() {

		        // Database URL, username, and password
		        String url = "jdbc:mysql://localhost:3306/libraryUserData";
		        String username = "root";
		        String password = "zephrus_02";

		        // Create a connection to the database
		        try {
		            Connection connection = DriverManager.getConnection(url, username, password);

		            // Execute a query
		            String query = "SELECT id FROM userdetails WHERE username = ?";
		            try (PreparedStatement statement = connection.prepareStatement(query)) {
		                statement.setString(1, UsernameFieldData);

		                // Execute the query
		                ResultSet resultSet = statement.executeQuery();

		                // Check if a user with the provided credentials was found
		                if (resultSet.next()) {
		                    userId = resultSet.getInt("id");
		                    System.out.println(userId);
		                    
		                    resultSet.close();
		                    statement.close();
		                    connection.close();
		                }
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }

		    }
}