package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import databasePart1.DatabaseHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AnswerList {
    private final DatabaseHelper databaseHelper;
    public String userId;
    public int qId; // Question ID for which to load answers

    public AnswerList(DatabaseHelper databaseHelper, String userId, int qId) {
        this.databaseHelper = databaseHelper;
        this.userId = userId;
        this.qId = qId;
    }
    
    // Retrieve all answers for the given question from the database.
    public List<Answer> getAllAnswers() {
        List<Answer> answers = new ArrayList<>();
        try {
            databaseHelper.connectToDatabase();
            ResultSet rs = databaseHelper.getAnswers(qId);
            while (rs.next()) {
                int ansId = rs.getInt("ansId");
                int questionId = rs.getInt("qId");
                int sId = rs.getInt("sId");
                String answerFrom = rs.getString("answerFrom");
                String answerText = rs.getString("answerText");
                Answer answer = new Answer(ansId, questionId, sId, answerFrom, answerText);
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }
    
    // Displays the Answer List page.
    public void show(Stage primaryStage) {
        primaryStage.setTitle("Answers for Question " + qId);
        
        
        // Create a ListView to display answers.
        ListView<String> listView = new ListView<>();
        ObservableList<Integer> idItems = FXCollections.observableArrayList();
        ObservableList<String> items = FXCollections.observableArrayList();
        List<Answer> answers = getAllAnswers();
        for (Answer a : answers) {
            items.add("Answer by " + a.getAnswerFrom() + ":\n" + a.getAnswerText());
            idItems.add(a.getAnsId());
        }
        listView.setItems(items);
        listView.setOnMouseClicked(event -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                // Assuming your Question class has a getId() method.
           
                int answerId = idItems.get(index);
                
                
                new ReviewerList(databaseHelper, userId, answerId).show(primaryStage);
            }
        });
        
        // Create a "Post Answer" button.
        Button postAnswerButton = new Button("Post Answer");
        postAnswerButton.setOnAction(e -> {
           new PostAnswer(databaseHelper, userId, qId).show(primaryStage);
        });
        
        // Button to return to the Question List.
        Button returnButton = new Button("Return to Questions");
        returnButton.setOnAction(e -> {
            new QuestionList(databaseHelper, userId).show(primaryStage);
        });
        
        // Place the buttons in a toolbar.
        ToolBar toolBar = new ToolBar(postAnswerButton, returnButton);
        
        // Layout using a BorderPane.
        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        root.setCenter(listView);
        BorderPane.setMargin(listView, new Insets(10));
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}