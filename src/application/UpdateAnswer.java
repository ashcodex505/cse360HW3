package application;

import databasePart1.DatabaseHelper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UpdateAnswer {
    private final DatabaseHelper databaseHelper;
    private String userId;
    private Answer answer; // The answer to update

    public UpdateAnswer(DatabaseHelper databaseHelper, String userId, Answer answer) {
        this.databaseHelper = databaseHelper;
        this.userId = userId;
        this.answer = answer;
    }

    public void show(Stage primaryStage) {
        primaryStage.setTitle("Update Answer");

        Label answerFromLabel = new Label("Answer From:");
        TextField answerFromField = new TextField(answer.getAnswerFrom());

        Label answerTextLabel = new Label("Answer Text:");
        TextArea answerTextArea = new TextArea(answer.getAnswerText());

        Button updateButton = new Button("Update");
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
        grid.add(updateButton, 1, 2);
        grid.add(cancelButton, 1, 3);
        grid.add(feedbackLabel, 1, 4);

        updateButton.setOnAction(e -> {
            String newAnswerFrom = answerFromField.getText();
            String newAnswerText = answerTextArea.getText();
            if (newAnswerFrom == null || newAnswerFrom.trim().isEmpty() ||
                newAnswerText == null || newAnswerText.trim().isEmpty()) {
                feedbackLabel.setText("Error: Fields cannot be empty.");
                return;
            }
            try {
                databaseHelper.updateAnswer(answer.getAnsId(), newAnswerFrom, newAnswerText);
                feedbackLabel.setText("Answer updated successfully!");
                new AnswerList(databaseHelper, userId, answer.getQId()).show(primaryStage);
            } catch (Exception ex) {
                feedbackLabel.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelButton.setOnAction(e -> new AnswerList(databaseHelper, userId, answer.getQId()).show(primaryStage));

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}