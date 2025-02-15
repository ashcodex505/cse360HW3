package application;

import databasePart1.DatabaseHelper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PostAnswer {
    private final DatabaseHelper databaseHelper;
    public String userId;
    public int qId;
    
    public PostAnswer(DatabaseHelper databaseHelper, String userId, int qId) {
        this.databaseHelper = databaseHelper;
        this.userId = userId;
        this.qId = qId;
    }
    
    public void show(Stage primaryStage) {
        primaryStage.setTitle("Post an Answer for Question " + qId);

        Label answerFromLabel = new Label("Answer From:");
        TextField answerFromField = new TextField();

        Label answerTextLabel = new Label("Answer Text:");
        TextArea answerTextArea = new TextArea();

        Button postButton = new Button("Post Answer");
        Button cancelButton = new Button("Cancel");
        Label feedbackLabel = new Label();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(answerFromLabel, 0, 0);
        grid.add(answerFromField, 1, 0);
        grid.add(answerTextLabel, 0, 1);
        grid.add(answerTextArea, 1, 1);
        grid.add(postButton, 1, 2);
        grid.add(cancelButton, 1, 3);
        grid.add(feedbackLabel, 1, 4);

        postButton.setOnAction(e -> {
            String answerFrom = answerFromField.getText();
            String answerText = answerTextArea.getText();

            if (answerFrom == null || answerFrom.trim().isEmpty()) {
                feedbackLabel.setText("Error: 'Answer From' is required.");
                return;
            }
            if (answerText == null || answerText.trim().isEmpty()) {
                feedbackLabel.setText("Error: Answer Text is required.");
                return;
            }

            try {
                databaseHelper.createAnswer(qId, Integer.parseInt(userId), answerFrom, answerText);
                feedbackLabel.setText("Answer posted successfully!");
                answerFromField.clear();
                answerTextArea.clear();
                new AnswerList(databaseHelper, userId, qId).show(primaryStage);
            } catch (Exception ex) {
                feedbackLabel.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelButton.setOnAction(e -> new AnswerList(databaseHelper, userId, qId).show(primaryStage));

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}