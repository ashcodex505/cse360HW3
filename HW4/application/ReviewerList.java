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
import javafx.stage.Modality;

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

public class ReviewerList {
    private final DatabaseHelper databaseHelper;
    public String userId;
    public int aId; // Question ID for which to load answers

    public ReviewerList(DatabaseHelper databaseHelper, String userId, int aId) {
        this.databaseHelper = databaseHelper;
        this.userId = userId;
        this.aId = aId;
    }
    
    // Retrieve all answers for the given question from the database.
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
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        try {
            databaseHelper.connectToDatabase();
            ResultSet rs = databaseHelper.getReviews(aId);
            while (rs.next()) {
                int rId = rs.getInt("rId");
                int aId = rs.getInt("aId");
                int sId = rs.getInt("sId");
                String reviewFrom = rs.getString("reviewFrom");
                String reviewText = rs.getString("reviewText");
                Review review = new Review(rId, aId, sId, reviewFrom, reviewText);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    
    // Displays the Answer List page.
    public void show(Stage primaryStage) {
        primaryStage.setTitle("Reviews for Answer " + aId);
        
        // Create a ListView to display answers.
        ListView<String> listView = new ListView<>();
        ObservableList<Integer> idItems = FXCollections.observableArrayList();
        ObservableList<String> items = FXCollections.observableArrayList();
        List<Review> reviews = getAllReviews();
        for (Review r : reviews) {
            items.add("Answer by " + r.getReviewFrom() + ":\n" + r.getReviewText());
            idItems.add(r.getRId());
        }
        listView.setItems(items);
        
        // Create a "Post Answer" button.
        Button postReviewButton = new Button("Create Review");
        postReviewButton.setOnAction(e -> {
           new PostReview(databaseHelper, userId, aId).show(primaryStage);
        });
        Button updateReviewButton = new Button("Update Selected");
        updateReviewButton.setOnAction(e -> {
        	int index = listView.getSelectionModel().getSelectedIndex();
            if (index < 0) {
                showNonModalAlert("Please select a question to update.", 5);
                listView.setOnMouseClicked(event -> {
                	int indexFinal = listView.getSelectionModel().getSelectedIndex();
                	Integer rId = idItems.get(indexFinal);
                    
                    try {
    					ResultSet rs = databaseHelper.getReview(rId);
    					while (rs.next()) {
    		                String title = rs.getString("reviewFrom");
    		                String description = rs.getString("reviewText");
    		                String ansId = rs.getString(aId);
    		                String sId = rs.getString("sId");
    		                
    		                Review r = new Review(rId, Integer.parseInt(ansId), Integer.parseInt(sId), title, description);
//    		                new UpdateReview(databaseHelper, userId, r).show(primaryStage);
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
        
       
        
        // Button to return to the Question List.
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
        	new SetupLoginSelectionPage(databaseHelper).show(primaryStage);
           
        });
        
        // Place the buttons in a toolbar.
        ToolBar toolBar = new ToolBar(postReviewButton, returnButton, updateReviewButton);
        
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