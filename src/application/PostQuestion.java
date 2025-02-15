package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import databasePart1.*;

public class PostQuestion{
	private final DatabaseHelper databaseHelper;
	private QuestionList questions;
	private String userId;
	public PostQuestion(DatabaseHelper databaseHelper, String userId) {
        this.databaseHelper = databaseHelper;
        this.userId = userId;
        this.questions = new QuestionList(databaseHelper, userId);
    }
	;
	
	public void show(Stage primaryStage) {
		primaryStage.setTitle("Post a Question");

        // UI Components
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();

        Label descLabel = new Label("Description:");
        TextArea descArea = new TextArea();

        Button postButton = new Button("Post Question");
        Button cancelButton = new Button("Cancel");
        Label feedbackLabel = new Label();

        // Layout using GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(descLabel, 0, 1);
        grid.add(descArea, 1, 1);
        grid.add(postButton, 1, 2);
        grid.add(cancelButton, 1, 3);
        grid.add(feedbackLabel, 1, 3);

        // Button event handler for posting a question
        postButton.setOnAction(e -> {
            String title = titleField.getText();
            String description = descArea.getText();

            // Validate input fields
            if (title == null || title.trim().isEmpty()) {
                feedbackLabel.setText("Error: Title is required.");
                return;
            }
            if (description == null || description.trim().isEmpty()) {
                feedbackLabel.setText("Error: Description is required.");
                return;
            }

            try {
                // Create a new Question object and add it to the collection
                Question newQuestion = new Question(title, description, "", userId);
                questions.addQuestion(newQuestion);
                feedbackLabel.setText("Question posted successfully  " );

                // Clear input fields after successful post
                titleField.clear();
                descArea.clear();
                new QuestionList(databaseHelper,userId).show(primaryStage);
            } catch (IllegalArgumentException ex) {
                feedbackLabel.setText("Error: " + ex.getMessage());
            }
        });
        cancelButton.setOnAction(e -> {
            new QuestionList(databaseHelper, userId).show(primaryStage);
        });
        

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}