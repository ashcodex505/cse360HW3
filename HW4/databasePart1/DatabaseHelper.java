package databasePart1;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import application.User;


/**
 * The DatabaseHelper class is responsible for managing the connection to the database,
 * performing operations such as user registration, login validation, and handling invitation codes.
 */
public class DatabaseHelper {

	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/FoundationDatabase";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	private Connection connection = null;
	private Statement statement = null; 
	//	PreparedStatement pstmt

	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement(); 
			// You can use this command to clear the database and restart from fresh.
			//statement.execute("DROP ALL OBJECTS");

			createTables();  // Create the necessary tables if they don't exist
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}

	private void createTables() throws SQLException {
	    // Create the users table
		String userTable = "CREATE TABLE IF NOT EXISTS cse360users ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "userName VARCHAR(255) UNIQUE, "
				+ "password VARCHAR(255), "
				+ "role VARCHAR(20))";
		statement.execute(userTable);

	    // Create the invitation codes table
	    String invitationCodesTable = "CREATE TABLE IF NOT EXISTS InvitationCodes ("
	            + "code VARCHAR(10) PRIMARY KEY, "
	            + "isUsed BOOLEAN DEFAULT FALSE, "
	            + "expirationTime TIMESTAMP)";              // Expiration time for invitation code
	    statement.execute(invitationCodesTable);
	    
	    
	    // Password Reset Tables
	    String passwordReset  = " CREATE TABLE IF NOT EXISTS PasswordResetForm ("
	    	   +  "id INT AUTO_INCREMENT PRIMARY KEY,"
	    	   +  "userName VARCHAR(255) NOT NULL,"
	    	   + "otp VARCHAR(10),"
	    	   + "isProcessed BOOLEAN DEFAULT FALSE)";
	    	statement.execute(passwordReset);
//	        statement.execute("DROP TABLE IF EXISTS Answers");
//		    statement.execute("DROP TABLE IF EXISTS Questions");
	    	String questionsTable = "CREATE TABLE IF NOT EXISTS Questions ("
	                + "id INT AUTO_INCREMENT PRIMARY KEY, "
	                + "sid INT, "
	                + "qTitle VARCHAR(255), "
	                + "qDesc VARCHAR(1000), "
	                + "FOREIGN KEY (sid) REFERENCES cse360users(id))";
	        statement.execute(questionsTable);
	        String answersTable = "CREATE TABLE IF NOT EXISTS Answers ("
	                + "ansId INT AUTO_INCREMENT PRIMARY KEY, "
	                + "qId INT, "
	                + "sId INT, "
	                + "answerFrom VARCHAR(255), "
	                + "answerText VARCHAR(1000), "
	                + "FOREIGN KEY (qId) REFERENCES Questions(id) ON DELETE CASCADE, "
	                + "FOREIGN KEY (sId) REFERENCES cse360users(id))";
	        statement.execute(answersTable);
//	        statement.execute("DROP TABLE IF EXISTS Reviews");
////		    statement.execute("DROP TABLE IF EXISTS Questions");
	        String reviewsTable = "CREATE TABLE IF NOT EXISTS Reviews ("
	                + "rId INT AUTO_INCREMENT PRIMARY KEY, "
	                + "aId INT, "
	                + "sId INT, "
	                + "reviewFrom VARCHAR(255), "
	                + "reviewText VARCHAR(1000), "
	                + "FOREIGN KEY (aId) REFERENCES Answers(ansId) ON DELETE CASCADE, "
	                + "FOREIGN KEY (sId) REFERENCES cse360users(id))";
	        statement.execute(reviewsTable);
	        
	        String messagesTable = "CREATE TABLE IF NOT EXISTS Messages ("
	                + "id INT AUTO_INCREMENT PRIMARY KEY, "
	                + "senderId INT NOT NULL, "
	                + "receiverId INT NOT NULL, "
	                + "messageText VARCHAR(1000), "
	                + "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
	                + "FOREIGN KEY (senderId) REFERENCES cse360users(id), "
	                + "FOREIGN KEY (receiverId) REFERENCES cse360users(id))";
	        statement.execute(messagesTable);
	    
	    
	    
	}


	



	// Check if the database is empty
	public boolean isDatabaseEmpty() throws SQLException {
		String query = "SELECT COUNT(*) AS count FROM cse360users";
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			return resultSet.getInt("count") == 0;
		}
		return true;
	}
	public void updateQuestion(int qId, String newTitle, String newDesc) throws SQLException {
	    String query = "UPDATE Questions SET qTitle = ?, qDesc = ? WHERE id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, newTitle);
	        pstmt.setString(2, newDesc);
	        pstmt.setInt(3, qId);
	        pstmt.executeUpdate();
	    }
	}
	public ResultSet getAnswers(int qId) throws SQLException {
	    String query = "SELECT * FROM Answers WHERE qId = ?";
	    PreparedStatement pstmt = connection.prepareStatement(query);
	    pstmt.setInt(1, qId);
	    return pstmt.executeQuery();
	}
	
	public ResultSet getReviews(int aId) throws SQLException {
	    String query = "SELECT * FROM Reviews WHERE aId = ?";
	    PreparedStatement pstmt = connection.prepareStatement(query);
	    pstmt.setInt(1, aId);
	    return pstmt.executeQuery();
	}
	
	public ResultSet getReview(int rId) throws SQLException {
	    String query = "SELECT * FROM Reviews WHERE rId = ?";
	    PreparedStatement pstmt = connection.prepareStatement(query);
	    pstmt.setInt(1, rId);
	    return pstmt.executeQuery();
	}
	public ResultSet getAllReviews() throws SQLException {
        String query = "SELECT * FROM Reviews";
        return statement.executeQuery(query);
    }
	
	public void sendMessage(int senderId, int receiverId, String messageText) throws SQLException {
	    String query = "INSERT INTO Messages (senderId, receiverId, messageText) VALUES (?, ?, ?)";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, senderId);
	        pstmt.setInt(2, receiverId);
	        pstmt.setString(3, messageText);
	        pstmt.executeUpdate();
	    }
	}
	
	// Get messages between user
		public ResultSet getMessagesBetweenUsers(int user1Id, int user2Id) throws SQLException {
		    String query = "SELECT * FROM Messages "
		                 + "WHERE (senderId = ? AND receiverId = ?) OR (senderId = ? AND receiverId = ?) "
		                 + "ORDER BY timestamp ASC";
		    PreparedStatement pstmt = connection.prepareStatement(query);
		    pstmt.setInt(1, user1Id);
		    pstmt.setInt(2, user2Id);
		    pstmt.setInt(3, user2Id);
		    pstmt.setInt(4, user1Id);
		    return pstmt.executeQuery();
		}
		
	
	public void updateReview(int rId, String newReviewFrom, String newReviewText) throws SQLException {
	    String query = "UPDATE Reviews SET reviewFrom = ?, reviewText = ? WHERE rId = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, newReviewFrom);
	        pstmt.setString(2, newReviewText);
	        pstmt.setInt(3, rId);
	        pstmt.executeUpdate();
	    }
	}
	
	public ResultSet getAllMessages() throws SQLException {
	    String query = "SELECT m.*, u1.userName AS senderName, u2.userName AS receiverName " +
	                   "FROM Messages m " +
	                   "JOIN cse360users u1 ON m.senderId = u1.id " +
	                   "JOIN cse360users u2 ON m.receiverId = u2.id " +
	                   "ORDER BY timestamp ASC";
	    return statement.executeQuery(query);
	}
	
	public void deleteReview(int rId) throws SQLException {
	    String query = "DELETE FROM Reviews WHERE rId = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, rId);
	        pstmt.executeUpdate();
	    }
	}
	
	public void createReview(int aId, int sId, String reviewFrom, String reviewText) throws SQLException {
	    String insertAnswer = "INSERT INTO Reviews (aId, sId, reviewFrom, reviewText) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement pstmt = connection.prepareStatement(insertAnswer)) {
	        pstmt.setInt(1, aId);
	        pstmt.setInt(2, sId);
	        pstmt.setString(3, reviewFrom);
	        pstmt.setString(4, reviewText);
	        pstmt.executeUpdate();
	    }
	}
	public void deleteQuestion(int qId) throws SQLException {
	    String query = "DELETE FROM Questions WHERE id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, qId);
	        pstmt.executeUpdate();
	    }
	}
	
	public void updateAnswer(int ansId, String newAnswerFrom, String newAnswerText) throws SQLException {
	    String query = "UPDATE Answers SET answerFrom = ?, answerText = ? WHERE ansId = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, newAnswerFrom);
	        pstmt.setString(2, newAnswerText);
	        pstmt.setInt(3, ansId);
	        pstmt.executeUpdate();
	    }
	}
	public void deleteAnswer(int ansId) throws SQLException {
	    String query = "DELETE FROM Answers WHERE ansId = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, ansId);
	        pstmt.executeUpdate();
	    }
	}
	public void createAnswer(int qId, int sId, String answerFrom, String answerText) throws SQLException {
	    String insertAnswer = "INSERT INTO Answers (qId, sId, answerFrom, answerText) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement pstmt = connection.prepareStatement(insertAnswer)) {
	        pstmt.setInt(1, qId);
	        pstmt.setInt(2, sId);
	        pstmt.setString(3, answerFrom);
	        pstmt.setString(4, answerText);
	        pstmt.executeUpdate();
	    }
	}

	// Registers a new user in the database.
	public void register(User user) throws SQLException {
		String insertUser = "INSERT INTO cse360users (userName, password, role) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getRole());
			pstmt.executeUpdate();
		}
	}
	
	
	// Updates the password from OTP
	public void updatePassword(String userName, String newPassword) throws SQLException {
	    String updatePass = "UPDATE cse360users SET password = ? WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(updatePass)) {
	        pstmt.setString(1, newPassword); // Set new password first
	        pstmt.setString(2, userName);    // Set username second
	        pstmt.executeUpdate();
	    }
	}
	// Fetch all users except the given admin user
	public ResultSet getUsersExcept(String adminUserName) throws SQLException {
	    String query = "SELECT userName, role FROM cse360users WHERE userName <> ?";
	    PreparedStatement pstmt = connection.prepareStatement(query);
	    pstmt.setString(1, adminUserName);
	    return pstmt.executeQuery();
	}


	// Validates a user's login credentials.
	public boolean login(User user) throws SQLException {
		String query = "SELECT * FROM cse360users WHERE userName = ? AND password = ? AND role = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getRole());
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	// Checks if a user already exists in the database based on their userName.
	public boolean doesUserExist(String userName) {
	    String query = "SELECT COUNT(*) FROM cse360users WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // If the count is greater than 0, the user exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume user doesn't exist
	}
	
	
	public ResultSet getQuestions(int qId, int userId) throws SQLException {
	    String query = "SELECT * FROM Questions WHERE id = ? AND sId = ?";
	    PreparedStatement pstmt = connection.prepareStatement(query);
	    pstmt.setInt(1, qId);
	    pstmt.setInt(2, userId);
	    return pstmt.executeQuery();
	}
	
	public ResultSet getAllAnswers() throws SQLException {
	    String query = "SELECT * FROM Answers";
	    return statement.executeQuery(query);
	}
	//gets all questions
	public ResultSet getAllQuestions() throws SQLException {
        String query = "SELECT * FROM Questions";
        return statement.executeQuery(query);
    }
	public void addQuestion(int sid, String qTitle, String qDesc) throws SQLException {
	    String insertQuestion = "INSERT INTO Questions (sid, qTitle, qDesc) VALUES (?, ?, ?)";
	    try (PreparedStatement pstmt = connection.prepareStatement(insertQuestion)) {
	        pstmt.setInt(1, sid);
	        pstmt.setString(2, qTitle);
	        pstmt.setString(3, qDesc);
	        pstmt.executeUpdate();
	    }
	}
	
	public void changeUserRole(String userName, String newRole) throws SQLException {
	    // First, check if the user exists
	    String checkUserExistsQuery = "SELECT COUNT(*) FROM cse360users WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(checkUserExistsQuery)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next() && rs.getInt(1) == 0) {
	            throw new SQLException("User does not exist: " + userName);
	        }
	    }

	    // Ensure the new role is valid (add any logic here for allowed roles)
	    if (newRole == null || newRole.isEmpty()) {
	        throw new SQLException("Invalid role: " + newRole);
	    }

	    // Update the user's role in the database
	    String updateRoleQuery = "UPDATE cse360users SET role = ? WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(updateRoleQuery)) {
	        pstmt.setString(1, newRole);
	        pstmt.setString(2, userName);
	        pstmt.executeUpdate();
	    }
	}
	
	public String getUserId(String userName) {
		String query = "SELECT id FROM cse360users WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getString("id"); // Return the role if user exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
		
	}
	// Retrieves the role of a user from the database using their UserName.
	public String getUserRole(String userName) {
	    String query = "SELECT role FROM cse360users WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getString("role"); // Return the role if user exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	
	// Generates a new invitation code and inserts it into the database with an expiration time.
	public String generateInvitationCode() {
	    String code = UUID.randomUUID().toString().substring(0, 4); // Generate a random 4-character code
	    String query = "INSERT INTO InvitationCodes (code, isUsed) VALUES (?, ?)";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        // Assuming new codes are not used yet
	        pstmt.setBoolean(2, false);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return code;
	}

	
	// Validates an invitation code to check if it is unused and not expired.
	public boolean validateInvitationCode(String code) {
	    String query = "SELECT * FROM InvitationCodes WHERE code = ? AND isUsed = FALSE";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        //pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis())); // Current time
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            markInvitationCodeAsUsed(code); // Mark code as used
	            return true;
	        }
	     }catch (SQLException e) {
	        System.err.println("Database error: " + e.getMessage());
	        throw new RuntimeException("Failed to execute database operation", e);
	    }

	    return false;
	}

	
	// Marks the invitation code as used in the database.
	private void markInvitationCodeAsUsed(String code) {
	    String query = "UPDATE InvitationCodes SET isUsed = TRUE WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// Generates OTP for a user who requested a password reset
	public String setOneTimePassword(String userName) throws SQLException {
	    String otp = UUID.randomUUID().toString().substring(0, 6); // Generate a 6-character OTP
	    String query = "UPDATE PasswordResetForm SET otp = ?, isProcessed = TRUE WHERE userName = ? AND isProcessed = FALSE";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, otp);
	        pstmt.setString(2, userName);
	        int rowsUpdated = pstmt.executeUpdate();
	        return (rowsUpdated > 0) ? otp : null; // Return OTP if update was successful
	    }
	}

	
	// Validates the one-time password (OTP) for the user.
	public boolean validateOneTimePassword(String userName, String otp) throws SQLException {
	    String query = "SELECT * FROM PasswordResetForm WHERE userName = ? AND otp = ? AND isProcessed = TRUE";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        pstmt.setString(2, otp);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	        	clearOneTimePassword(userName);
	            return true;
	        }
	    }catch (SQLException e) {
	        System.err.println("Database error: " + e.getMessage());
	        throw new RuntimeException("Failed to execute database operation", e);
	    }
	    return false;
	}


	// Clears OTP after the password is reset
	public void clearOneTimePassword(String userName) throws SQLException {
	    String query = "DELETE FROM PasswordResetForm WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        pstmt.executeUpdate();
	    }
	}


	
	// Adds a password reset request to the database
	public void addPasswordResetRequest(String userName) throws SQLException {
	    String query = "INSERT INTO PasswordResetForm (userName) VALUES (?)";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        pstmt.executeUpdate();
	    }
	}

	// Fetches all password reset requests for the admin dashboard
	public ResultSet getResetRequests() throws SQLException {
	    String query = "SELECT id, userName FROM PasswordResetForm WHERE isProcessed = FALSE";
	    return statement.executeQuery(query);
	}

	
	
	// Closes the database connection and statement.
	public void closeConnection() {
		try{ 
			if(statement!=null) statement.close(); 
		} catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		try { 
			if(connection!=null) connection.close(); 
		} catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}

}
