package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;
import databasePart1.DatabaseHelper;

/**
 * PasswordResetPage - Allows users to log in with an OTP.
 */
public class PasswordResetPage {
	private final DatabaseHelper databaseHelper;
	 public PasswordResetPage(DatabaseHelper databaseHelper) {
	        this.databaseHelper = databaseHelper;
	    } 

    public void show(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label instructionLabel = new Label("Enter your username and OTP:");
        instructionLabel.setStyle("-fx-font-size: 14px;");

        // Input fields 
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter username");
        userNameField.setMaxWidth(250);

        TextField otpField = new TextField();
        otpField.setPromptText("Enter OTP");
        otpField.setMaxWidth(250);
        
        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("Enter New Password"); 
        newPassword.setMaxWidth(250);
        
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: red;");

        Button loginButton = new Button("Login with OTP");
        loginButton.setOnAction(e -> {
            String userName = userNameField.getText();
            String otp = otpField.getText();
            String password = newPassword.getText();
            
            if (userName.isEmpty() || otp.isEmpty()) {
                messageLabel.setText("Username and OTP cannot be empty.");
            } else {
                try {
                	// After the OTP is validate, the new password will be sent to the database and the OTP will deleted
                    if (databaseHelper.validateOneTimePassword(userName, otp)) {
                        databaseHelper.updatePassword(userName, password); 

                        showAlert("Login Successful", "You have successfully logged in with OTP.");

                        // Redirect user to log in board
                    	new UserLoginPage(databaseHelper).show(primaryStage);

                        
                    } else {
                        messageLabel.setText("Invalid OTP or expired.");
                    }
                } catch (SQLException ex) {
                    messageLabel.setText("Database error: " + ex.getMessage());
                }
            }
        });


        Button returnHome = new Button("Return Home");

	    
	    returnHome.setOnAction( a -> {
        	new SetupLoginSelectionPage(databaseHelper).show(primaryStage);
        });

        layout.getChildren().addAll(instructionLabel, userNameField, otpField, newPassword ,loginButton, messageLabel, returnHome);

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("OTP Login");
    }

  // This the pop up feature in JAVA FX
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

  
}
