package application;

public class Answer {
    private int ansId;
    private int qId;
    private int sId;
    private String answerFrom;
    private String answerText;
    
    public Answer(int ansId, int qId, int sId, String answerFrom, String answerText) {
    	if (answerFrom == null || answerFrom.trim().isEmpty()) {
            throw new IllegalArgumentException("AnswerFrom cannot be empty.");
        }
        if (answerText== null || answerText.trim().isEmpty()) {
            throw new IllegalArgumentException("answerText cannot be empty.");
        }
        
       
    	this.ansId = ansId;
        this.qId = qId;
        this.sId = sId;
        this.answerFrom = answerFrom;
        this.answerText = answerText;
    }
    
    // Getters
    public int getAnsId() {
        return ansId;
    }
    
    public int getQId() {
        return qId;
    }
    
    public int getSId() {
        return sId;
    }
    
    public String getAnswerFrom() {
        return answerFrom;
    }
    
    public String getAnswerText() {
        return answerText;
    }
}