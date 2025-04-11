package JUnit;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import databasePart1.DatabaseHelper;

public class staffRoleJUnitTest {

    private DatabaseHelper dbManager;

    @Before
    public void initialize() throws Exception {
        dbManager = new DatabaseHelper();
        dbManager.connectToDatabase();
        
        // Prepare a clean state by wiping all records.
       
    }

    @After
    public void cleanup() throws Exception {
        dbManager.closeConnection();
    }
    
    @Test
    public void testReviewLifecycle() throws SQLException {
        // Step 1: Create a new question entry.
        String questionAuthor = "Alice";
        String questionHeading = "What is JUnit?";
        String questionDetails = "I am curious about how JUnit functions.";
        
        
        // Retrieve the question's unique identifier.
        ResultSet questionsResultSet = dbManager.getAllQuestions();
        int qId = -1;
        int sId = -1;
        if (questionsResultSet.next()) {
            qId = questionsResultSet.getInt("id");
            sId = questionsResultSet.getInt("sId");
        }
        dbManager.addQuestion(qId, questionAuthor, questionDetails);
        assertTrue("The question identifier must be valid", qId != -1);
        
        // Step 2: Insert an answer for the created question.
        String responderName = "Bob";
        String responseContent = "JUnit is a unit testing framework for Java applications.";
        dbManager.createAnswer(qId, sId, responderName, responseContent);
        
        // Retrieve the answer's unique identifier.
        ResultSet answersResultSet = dbManager.getAllAnswers();
        int ansId = -1;
        if (answersResultSet.next()) {
            ansId = answersResultSet.getInt("ansId");
        }
        assertTrue("The answer identifier must be valid", ansId != -1);
        
        // Step 3: Post a review for the provided answer.
        String reviewerName = "Carol";
        String initialFeedback = "Insightful answer and really helpful!";
        dbManager.createReview(ansId, qId, reviewerName, initialFeedback);
        
        // Confirm that the review record was successfully added.
        ResultSet reviewsResultSet = dbManager.getAllReviews();
        int revId = -1;
        boolean reviewFound = false;
        while (reviewsResultSet.next()) {
            if (reviewsResultSet.getString("reviewFrom").equals(reviewerName)) {
                revId = reviewsResultSet.getInt("rId");
                reviewFound = true;
            }
        }
        assertTrue("The review must be successfully recorded", reviewFound);
        assertTrue("The review identifier must be valid", revId != -1);
        
        // Step 4: Modify the content of the review.
        String newFeedback = "This answer is exceptionally well-explained!";
        dbManager.updateReview(revId, reviewerName, newFeedback);
        
        // Verify that the review content has been updated correctly.
        reviewsResultSet = dbManager.getAllReviews();
        boolean updateVerified = false;
        while (reviewsResultSet.next()){
            if(reviewsResultSet.getInt("rId") == revId && reviewsResultSet.getString("reviewText").equals(newFeedback)){
                updateVerified = true;
            }
        }
        assertTrue("The review should reflect the updated content", updateVerified);
        
        // Step 5: Remove the review from the database.
        dbManager.deleteReview(revId);
        reviewsResultSet = dbManager.getAllReviews();
        boolean reviewStillExists = false;
        while (reviewsResultSet.next()){
            if(reviewsResultSet.getInt("rId") == revId) {
                reviewStillExists = true;
            }
        }
        assertFalse("The review should be completely removed", reviewStillExists);
    }
}