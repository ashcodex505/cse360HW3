package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.util.Duration;
import databasePart1.*;



public class QuestionList {
	private final DatabaseHelper databaseHelper;
	public String userId;
//    private List<Question> questionList;

    public QuestionList(DatabaseHelper databaseHelper, String userId) {
    	this.databaseHelper = databaseHelper;
    	this.userId = userId;
//        questionList = new ArrayList<>();
    }

    // Adds a new question to the collection
    public void showNonModalAlert(String message, double seconds) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        // Set modality to NONE so that the alert does not block the rest of the UI.
        alert.initModality(Modality.NONE);
        alert.show();  // Show the alert non-modally

        // Create a PauseTransition that will close the alert after a specified delay.
        PauseTransition delay = new PauseTransition(Duration.seconds(seconds));
        delay.setOnFinished(event -> alert.close());
        delay.play();
    }
    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null.");
        }
         
       try {
		databaseHelper.connectToDatabase();
		databaseHelper.addQuestion(Integer.parseInt(userId), question.getTitle(), question.getDescription());
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
      
        
    }

    // Returns a copy of the question list
    public List<Question> getAllQuestions() {
    	List<Question> questions = new ArrayList<>();
    	
//        return new ArrayList<>(questionList);
    	try {
    		databaseHelper.connectToDatabase();
    		ResultSet rs = databaseHelper.getAllQuestions();
    		while (rs.next()) {
                String title = rs.getString("qTitle");
                String description = rs.getString("qDesc");
                String qId = rs.getString("id");
                String sId = rs.getString("sId");
                
                Question q = new Question(title, description, qId, sId);
                questions.add(q);
    		}
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return questions;
    }
    
    public void show(Stage primaryStage) {
    	primaryStage.setTitle("Questions List");

        // Create a ListView to display questions.
        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<String> idItems = FXCollections.observableArrayList();
        for (Question q : getAllQuestions()) {
            items.add("Title " + q.getTitle() + ": \n" + q.getDescription());
            idItems.add(q.getQuestionId());
        }
        listView.setItems(items);
        //when clicked navigate to answerList page
        listView.setOnMouseClicked(event -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                // Assuming your Question class has a getId() method.
                System.out.println(idItems.get(index));
                String selectedQId = idItems.get(index);
                int questionId = Integer.parseInt(selectedQId);
                
                
                new AnswerList(databaseHelper, userId, questionId).show(primaryStage);
            }
        });

        // Create a "Post Question" button.
        Button postQuestionButton = new Button("Post Question");
        postQuestionButton.setOnAction(e -> {
            // Navigate to the PostQuestionPage.
            new PostQuestion(databaseHelper, userId).show(primaryStage);
        });
        Button updateQuestionButton = new Button("Update Selected");
        updateQuestionButton.setOnAction(e -> {
        	int index = listView.getSelectionModel().getSelectedIndex();
            if (index < 0) {
                showNonModalAlert("Please select a question to update.", 5);
                listView.setOnMouseClicked(event -> {
                	int indexFinal = listView.getSelectionModel().getSelectedIndex();
                	String qId = idItems.get(indexFinal);
                    
                    try {
    					ResultSet rs = databaseHelper.getQuestions(Integer.parseInt(qId), Integer.parseInt(userId));
    					while (rs.next()) {
    		                String title = rs.getString("qTitle");
    		                String description = rs.getString("qDesc");
    		                String questionId = rs.getString("id");
    		                String sId = rs.getString("sId");
    		                
    		                Question q = new Question(title, description, qId, sId);
    		                new UpdateQuestion(databaseHelper, userId, q).show(primaryStage);
    					}
    				} catch (NumberFormatException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
                });
            }  
        });
        Button deleteQuestionButton = new Button("Delete Selected");
        
        deleteQuestionButton.setOnAction(e -> {
        	int index = listView.getSelectionModel().getSelectedIndex();
            if (index < 0) {
                showNonModalAlert("Please select a question to delete.", 15);
                listView.setOnMouseClicked(event -> {
                	int indexFinal = listView.getSelectionModel().getSelectedIndex();
                	String qId = idItems.get(indexFinal);
                    
                    try {
    					ResultSet rs = databaseHelper.getQuestions(Integer.parseInt(qId), Integer.parseInt(userId));
    					while (rs.next()) {
    		                String title = rs.getString("qTitle");
    		                String description = rs.getString("qDesc");
    		                String questionId = rs.getString("id");
    		                String sId = rs.getString("sId");
    		                
    		                Question q = new Question(title, description, qId, sId);
    		                
    		                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this question?", ButtonType.OK, ButtonType.CANCEL);
    		                confirmation.showAndWait().ifPresent(response -> {
    		                    if (response == ButtonType.OK) {
    		                        try {
    		                            databaseHelper.deleteQuestion(Integer.parseInt(questionId));
    		                            // Refresh the list after deletion.
    		                            new QuestionList(databaseHelper, userId).show(primaryStage);
    		                        } catch (SQLException ex) {
    		                            ex.printStackTrace();
    		                        }
    		                    }
    		                });
    		                
    					}
    				} catch (NumberFormatException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
                });
            }  
        });



        // Place the button in a toolbar at the top.
        ToolBar toolBar = new ToolBar(postQuestionButton,updateQuestionButton, deleteQuestionButton);
        Button returnHome = new Button("Return Home");
	    returnHome.setOnAction( a -> {
        	new SetupLoginSelectionPage(databaseHelper).show(primaryStage);
        });

        // Layout using a BorderPane.
        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        root.setCenter(listView);
        BorderPane.setMargin(listView, new Insets(10));

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
  
    }
    
    // Additional CRUD methods (update, delete) could be implemented here.
}