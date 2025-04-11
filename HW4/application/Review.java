package application;

public class Review {
    private int rId;
    private int aId;
    private int sId;
    private String reviewFrom;
    private String reviewText;
   
    public Review(int rId, int aId, int sId, String reviewFrom, String reviewText) {
    	if (reviewText == null || reviewFrom.trim().isEmpty()) {
            throw new IllegalArgumentException("AnswerFrom cannot be empty.");
        }
        if (reviewText== null || reviewText.trim().isEmpty()) {
            throw new IllegalArgumentException("answerText cannot be empty.");
        }
        
       
    	this.rId = rId;
        this.aId = aId;
        this.sId = sId;
        this.reviewFrom = reviewFrom;
        this.reviewText = reviewText;
    }
    
    // Getters
    public int getRId() {
        return rId;
    }
    
    public int getAId() {
        return aId;
    }
    
    public int getSId() {
        return sId;
    }
    
    public String getReviewFrom() {
        return reviewFrom;
    }
    
    public String getReviewText() {
        return reviewText;
    }
}