package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import databasePart1.*;

/**
 * The WelcomeLoginPage class displays a welcome screen for authenticated users.
 * <p>
 * This page provides options for users to navigate to their respective pages based on their role,
 * to generate invitation codes (for administrators), to return to the login selection page,
 * or to quit the application.
 * </p>
 */
public class WelcomeLoginPage {
    
    private final DatabaseHelper databaseHelper;

    /**
     * Constructs a WelcomeLoginPage instance with the specified database helper.
     *
     * @param databaseHelper the DatabaseHelper instance used for performing database operations
     */
    public WelcomeLoginPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * Displays the welcome login page on the provided primary stage for the specified user.
     * <p>
     * This method sets up the user interface components such as labels and buttons, and assigns
     * event handlers for navigation based on the user's role. Depending on the user's role, the
     * Continue button navigates the user to the corresponding page:
     * <ul>
     *   <li>Admin: navigates to the AdminHomePage.</li>
     *   <li>User/Reviewer: navigates to the UserHomePage.</li>
     *   <li>Staff: navigates to the StaffDashboard.</li>
     * </ul>
     * The page also includes an Invite button (displayed only for admin users),
     * a Return Home button to navigate back to the login selection page,
     * and a Quit button to exit the application.
     * </p>
     *
     * @param primaryStage the primary stage on which the welcome page will be displayed
     * @param user         the authenticated user whose role will determine the navigation flow
     */
    public void show(Stage primaryStage, User user) {
        VBox layout = new VBox(5);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Welcome message label
        Label welcomeLabel = new Label("Welcome!!");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Button to navigate to the user's respective page based on their role
        Button continueButton = new Button("Continue to your Page");
        continueButton.setOnAction(a -> {
            String role = user.getRole();
            System.out.println(role);
            
            if (role.equals("admin")) {
                new AdminHomePage(databaseHelper, user.getUserName()).show(primaryStage);
            } else if (role.equals("user") || role.equals("reviewer")) {
                new UserHomePage(databaseHelper, user.getUserName()).show(primaryStage);
            } else if (role.equals("staff")) {
                new StaffDashboard(databaseHelper, user.getUserName()).show(primaryStage);
            }
        });

        // Button to quit the application
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(a -> {
            databaseHelper.closeConnection();
            Platform.exit(); // Exit the JavaFX application
        });

        // "Invite" button for admin to generate invitation codes
        if ("admin".equals(user.getRole())) {
            Button inviteButton = new Button("Invite");
            inviteButton.setOnAction(a -> {
                new InvitationPage().show(databaseHelper, primaryStage);
            });
            layout.getChildren().add(inviteButton);
        }

        // Navigation button to return to the login selection page
        Button returnHome = new Button("Return Home");
        returnHome.setOnAction(a -> {
            new SetupLoginSelectionPage(databaseHelper).show(primaryStage);
        });

        // Add all components to the layout
        layout.getChildren().addAll(welcomeLabel, continueButton, quitButton, returnHome);
        Scene welcomeScene = new Scene(layout, 800, 400);

        // Set the scene and title on the primary stage
        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Welcome Page");
    }
}