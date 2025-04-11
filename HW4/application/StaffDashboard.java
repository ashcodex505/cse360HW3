package application;

import databasePart1.DatabaseHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The StaffDashboard class is responsible for displaying the dashboard used by staff members 
 * to review questions, answers, and private messages. It creates a styled user interface with buttons 
 * that trigger specific functionalities, such as viewing data and returning to the login selection page.
 */
public class StaffDashboard {

    private final DatabaseHelper dbHelper;
    private final String staffUsername;

    /**
     * Constructs a StaffDashboard instance with the specified database helper and staff username.
     *
     * @param dbHelper      the DatabaseHelper instance used for executing database operations
     * @param staffUsername the username of the staff member using the dashboard
     */
    public StaffDashboard(DatabaseHelper dbHelper, String staffUsername) {
        this.dbHelper = dbHelper;
        this.staffUsername = staffUsername;
    }

    /**
     * Displays the staff dashboard on the provided primary stage.
     * <p>
     * This method sets up the main container with a dark themed background, initializes various buttons 
     * with alternating styles, and assigns event handlers to each button to perform the respective operations:
     * <ul>
     *   <li>Review Questions: Opens a dialog displaying questions data.</li>
     *   <li>Review Answers: Opens a dialog displaying answers data.</li>
     *   <li>Review Private Messages: Opens a dialog showing an example private message.</li>
     *   <li>Return: Navigates back to the login selection page.</li>
     * </ul>
     * </p>
     *
     * @param primaryStage the primary stage on which the dashboard will be displayed
     */
    public void show(Stage primaryStage) {
        // Main container with a dark background, centered alignment, and updated font
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER); // Center align all components
        root.setStyle("-fx-background-color: #2c3e50; -fx-font-family: 'Times-New-Roman';");

        // Title label with updated font size and contrasting text color
        Label title = new Label("Staff Review Dashboard");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #e74c3c; -fx-padding: 15 0 25 0;");

        // Define two button styles to alternate button colors
        String purpleButtonStyle = "-fx-background-color: #800080; -fx-text-fill: white; -fx-font-size: 12px; " +
                "-fx-font-family: 'Times-New-Roman'; -fx-padding: 12 25; -fx-background-radius: 2; -fx-cursor: hand;";
        String redButtonStyle = "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; " +
                "-fx-font-family: 'Times-New-Roman'; -fx-padding: 12 25; -fx-background-radius: 2; -fx-cursor: hand;";

        // Create buttons and assign alternating styles
        Button revQuestButton = new Button("Review Questions");
        revQuestButton.setStyle(purpleButtonStyle);

        Button revAnsButton = new Button("Review Answers");
        revAnsButton.setStyle(redButtonStyle);

        Button revMsgsButton = new Button("Review Private Messages");
        revMsgsButton.setStyle(purpleButtonStyle);

        Button backButton = new Button("Return");
        backButton.setStyle(redButtonStyle);

        // Event handler for questions: shows a popup with questions data
        revQuestButton.setOnAction(e -> {
            try {
                ResultSet rs = dbHelper.getAllQuestions();
                showDataPopup("Questions", rs, "qTitle", "qDesc");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Event handler for answers: shows a popup with answers data
        revAnsButton.setOnAction(e -> {
            try {
                ResultSet rs = dbHelper.getAllAnswers();
                showDataPopup("Answers", rs, "answerFrom", "answerText");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Event handler for messages: shows a popup with message data (example string used)
        revMsgsButton.setOnAction(e -> {
            try {
                String rs = "Ash505404 ---> MKurse : Hey, how are you?";
                showMessagesPopup(rs);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Return button goes back to the login page
        backButton.setOnAction(e -> {
            new SetupLoginSelectionPage(dbHelper).show(primaryStage);
        });

        // Add all components to the main container
        root.getChildren().addAll(title, revQuestButton, revAnsButton, revMsgsButton, backButton);

        // Create and set the scene
        Scene scene = new Scene(root, 600, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Staff Dashboard");
    }

    /**
     * Displays a styled popup dialog containing data retrieved from the provided {@code ResultSet}.
     * <p>
     * This method builds a string using the provided field names from the result set and displays
     * the concatenated data in an alert dialog.
     * </p>
     *
     * @param title  the title of the popup dialog
     * @param rs     the ResultSet containing the data to display
     * @param field1 the first field name to retrieve data from each record
     * @param field2 the second field name to retrieve data from each record
     * @throws SQLException if there is an error processing the ResultSet
     */
    private void showDataPopup(String title, ResultSet rs, String field1, String field2) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        StringBuilder content = new StringBuilder();
        while (rs.next()) {
            content.append(rs.getString(field1)).append(": ")
                   .append(rs.getString(field2)).append("\n\n");
        }
        String output = content.toString().isEmpty() ? "No data found." : content.toString();
        alert.setContentText(output);

        // Updated styling for the popup dialog box: white background, rounded corners, padding, and dark text color
        alert.getDialogPane().setStyle("-fx-background-color: #ffffff; " +
                "-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #333333; " +
                "-fx-background-radius: 10; -fx-padding: 20;");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     * Displays a styled popup dialog for private messages.
     * <p>
     * This method shows an alert dialog containing the provided message string, using consistent 
     * styling as the data popup.
     * </p>
     *
     * @param message the string representing the private message(s) to be displayed
     * @throws SQLException if an error occurs while displaying the popup (included for signature consistency)
     */
    private void showMessagesPopup(String message) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Private Messages");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply the same updated styling for the message dialog box
        alert.getDialogPane().setStyle("-fx-background-color: #ffffff; " +
                "-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #333333; " +
                "-fx-background-radius: 10; -fx-padding: 20;");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}