package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;
 

/**
 * The SetupAdmin class handles the setup process for creating an administrator account.
 * This is intended to be used by the first user to initialize the system with admin credentials.
 */
public class AdminSetupPage {

	
	
    private final DatabaseHelper databaseHelper;
    
    Label passwordErrorLabel = new Label();
    
    Label userNameErrorLabel = new Label();

    public AdminSetupPage(DatabaseHelper databaseHelper) {
    	
        this.databaseHelper = databaseHelper;
        userNameErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        passwordErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
    }

    public void show(Stage primaryStage) {
    	// Input fields for userName and password
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter Admin userName");
        userNameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);

        Label errorLabel = new Label();
        
        Button setupButton = new Button("Setup");
        
        setupButton.setOnAction(a -> {
        	// Retrieve user input
            String userName = userNameField.getText();
            String password = passwordField.getText();
            try {
            	// Before the User is created, both the username and password must be validated by the UserReconizer and Password Evaluator class. 
            	// Since the two files are inside of HW1 application, we can just simply call them from their class. 
            	String userVerify = UserNameRecognizer.checkForValidUserName(userName);
            	String passVerify = PasswordEvaluator.evaluatePassword(password);
            	userNameErrorLabel.setText(userVerify);
        	    passwordErrorLabel.setText(passVerify);
            	if (!userVerify.isEmpty() || !passVerify.isEmpty() ) {
            	    userNameField.setText("");
            	    passwordField.setText("");
            	    return; // Stop further execution
            	}
            	
            	// Create a new User object with admin role and register in the database
            	User user=new User(userName, password, "admin");
            	
                databaseHelper.register(user);
                System.out.println("Administrator setup completed.");
                
                // Navigate to the Welcome Login Page
                new UserLoginPage(databaseHelper).show(primaryStage);
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        });

        VBox layout = new VBox(10, userNameField, passwordField, setupButton,userNameErrorLabel, passwordErrorLabel );
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("Administrator Setup");
        primaryStage.show();
    }
}
