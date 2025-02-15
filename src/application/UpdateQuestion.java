package application;

import databasePart1.DatabaseHelper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UpdateQuestion {
    private final DatabaseHelper databaseHelper;
    private String userId;
    private Question question; // The question to update

    public UpdateQuestion(DatabaseHelper databaseHelper, String userId, Question question) {
        this.databaseHelper = databaseHelper;
        this.userId = userId;
        this.question = question;
    }
    
    public void show(Stage primaryStage) {
        primaryStage.setTitle("Update Question");

        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField(question.getTitle());

        Label descLabel = new Label("Description:");
        TextArea descArea = new TextArea(question.getDescription());

        Button updateButton = new Button("Update");
        Button cancelButton = new Button("Cancel");
        Label feedbackLabel = new Label();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(descLabel, 0, 1);
        grid.add(descArea, 1, 1);
        grid.add(updateButton, 1, 2);
        grid.add(cancelButton, 1, 3);
        grid.add(feedbackLabel, 1, 4);

        updateButton.setOnAction(e -> {
            String newTitle = titleField.getText();
            String newDesc = descArea.getText();
            if (newTitle == null || newTitle.trim().isEmpty() ||
                newDesc == null || newDesc.trim().isEmpty()) {
                feedbackLabel.setText("Error: Title and Description cannot be empty.");
                return;
            }
            try {
                int qId = Integer.parseInt(question.getQuestionId());
                databaseHelper.updateQuestion(qId, newTitle, newDesc);
                feedbackLabel.setText("Question updated successfully!");
                new QuestionList(databaseHelper, userId).show(primaryStage);
            } catch (Exception ex) {
                feedbackLabel.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        cancelButton.setOnAction(e -> new QuestionList(databaseHelper, userId).show(primaryStage));
        
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}