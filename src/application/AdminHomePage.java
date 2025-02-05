package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import databasePart1.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Admin Page - Displays OTP requests for the admin to process.
 */
public class AdminHomePage {
    private final DatabaseHelper databaseHelper;

    public AdminHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    // This shows the admin who needs their password to be reset
    public void show(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label adminLabel = new Label("Hello, Admin!");
        adminLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // TableView to display OTP requests
        TableView<OtpRequest> otpTable = new TableView<>();
        otpTable.setPrefWidth(600);
        otpTable.setPrefHeight(200);

        
        TableColumn<OtpRequest, String> userNameColumn = new TableColumn<>("Username");
        userNameColumn.setCellValueFactory(data -> data.getValue().userNameProperty());

        otpTable.getColumns().add(userNameColumn);

        // Admin select the user and make an OTP for them 
        Button processButton = new Button("Generate OTP");
        processButton.setOnAction(e -> {
            OtpRequest selectedRequest = otpTable.getSelectionModel().getSelectedItem();
            if (selectedRequest != null) {
                try {
                    String otp = databaseHelper.setOneTimePassword(selectedRequest.getUserName()); //OTP generated call here
                    if (otp != null) {
                        showAlert("OTP Generated", "Generated OTP for " + selectedRequest.getUserName() + ": " + otp);
                        otpTable.setItems(getOtpRequests()); // Refresh table
                    }
                } catch (SQLException ex) {
                    showAlert("Error", "Could not generate OTP: " + ex.getMessage());
                }
            } else {
                showAlert("No Request Selected", "Please select a request.");
            }
        });

        
        Button returnHome = new Button("Return Home");

	    
	    returnHome.setOnAction( a -> {
        	new SetupLoginSelectionPage(databaseHelper).show(primaryStage);
        });
	    
        layout.getChildren().addAll(adminLabel, otpTable, processButton, returnHome);
        otpTable.setItems(getOtpRequests());

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("Admin Page");
    }

    // Displaying the user requests
    private ObservableList<OtpRequest> getOtpRequests() {
        ObservableList<OtpRequest> requests = FXCollections.observableArrayList();
        try {
            ResultSet rs = databaseHelper.getResetRequests();
            while (rs.next()) {
                String userName = rs.getString("userName");
                requests.add(new OtpRequest(userName));
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage());
        }
        return requests;
    }

    	// Pop up windows
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
