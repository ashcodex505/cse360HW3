package application;

import databasePart1.DatabaseHelper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PostReview {
    private final DatabaseHelper databaseHelper;
    public String userId;
    public int aId;
    
    public PostReview(DatabaseHelper databaseHelper, String userId, int aId) {
        this.databaseHelper = databaseHelper;
        this.userId = userId;
        this.aId = aId;
    }
    
    public void show(Stage primaryStage) {
        primaryStage.setTitle("Post a Review for Answer " + aId);

        Label reviewFromLabel = new Label("Review From:");
        TextField reviewFromField = new TextField();

        Label reviewTextLabel = new Label("Review Text:");
        TextArea reviewTextArea = new TextArea();

        Button postButton = new Button("Post Review");
        Button cancelButton = new Button("Cancel");
        Label feedbackLabel = new Label();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(reviewFromLabel, 0, 0);
        grid.add(reviewFromField, 1, 0);
        grid.add(reviewTextLabel, 0, 1);
        grid.add(reviewTextArea, 1, 1);
        grid.add(postButton, 1, 2);
        grid.add(cancelButton, 1, 3);
        grid.add(feedbackLabel, 1, 4);

        postButton.setOnAction(e -> {
            String reviewFrom = reviewFromField.getText();
            String reviewText = reviewTextArea.getText();

            if (reviewFrom == null || reviewFrom.trim().isEmpty()) {
                feedbackLabel.setText("Error: 'Reviewer From' is required.");
                return;
            }
            if (reviewText == null || reviewText.trim().isEmpty()) {
                feedbackLabel.setText("Error: Review Text is required.");
                return;
            }

            try {
                databaseHelper.createReview(aId, Integer.parseInt(userId), reviewFrom, reviewText);
                feedbackLabel.setText("Answer posted successfully!");
                reviewFromField.clear();
                reviewTextArea.clear();
                new ReviewerList(databaseHelper, userId, aId).show(primaryStage);
            } catch (Exception ex) {
                feedbackLabel.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelButton.setOnAction(e -> new ReviewerList(databaseHelper, userId, aId).show(primaryStage));

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}