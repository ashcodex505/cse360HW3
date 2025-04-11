package application;

/**
 * The User class represents a user entity in the system.
 * It contains the user's details such as userName, password, and role.
 */
public class Question {
  
    private String title;
    private String description;
    private String qId;
    private String sId;
    // Constructor with input validation
    public Question(String title, String description, String qId, String sId) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        
        this.title = title;
        this.description = description;
        this.qId = qId;
        this.sId = sId;
    }

    // Getters
    public String getUserId() {
        return sId;
    }
    public String getQuestionId() {
    	return qId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    // For debugging and display purposes
    
    
}