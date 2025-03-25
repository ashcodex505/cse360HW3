package automation;

import databasePart1.DatabaseHelper;
import application.User;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DatabaseHelperTestingAutomation is a standalone automated test application
 * that executes a suite of five tests on the {@link databasePart1.DatabaseHelper} class.
 * <p>
 * The five automated tests included in this suite are:
 * <ol>
 *   <li><b>Test 1 – Register and Login User:</b> Registers a user (if not already registered)
 *       and verifies that the login functionality works as expected.</li>
 *   <li><b>Test 2 – Check User Existence:</b> Confirms that after registration, the system
 *       correctly identifies the user’s existence in the database.</li>
 *   <li><b>Test 3 – Invitation Code Generation and Validation:</b> Generates an invitation code
 *       and validates that it can be used, marking it as used thereafter.</li>
 *   <li><b>Test 4 – Password Reset Workflow:</b> Simulates the password reset process by adding
 *       a reset request, generating a one-time password (OTP), validating the OTP, and confirming
 *       its clearance.</li>
 *   <li><b>Test 5 – Questions and Answers Operations:</b> Tests full CRUD operations for questions
 *       and answers, including adding, updating, and deleting operations.</li>
 * </ol>
 * <p>
 * <strong>Javadoc Inspiration:</strong><br>
 * This documentation style was inspired by the <a href="https://docs.oracle.com/javase/8/docs/api/">Oracle Java SE API Documentation</a>.
 * What is compelling about this example is its clarity and thoroughness: every class and method
 * is well-documented with clear descriptions of parameters, return values, and behavior,
 * making it very easy for developers to understand how to use the APIs.
 * <p>
 * To generate the Javadoc output for this file, use the following command (assuming your source is in the "src" directory):
 * <pre>
 * javadoc -d docs -sourcepath src automation.DatabaseHelperTestingAutomation.java
 * </pre>
 * This will create a folder called "docs" containing the HTML files of the Javadoc.
 *
 * @author 
 */
public class automated5Tests{

    /** Counter for the number of tests passed. */
    static int numPassed = 0;
    
    /** Counter for the number of tests failed. */
    static int numFailed = 0;

    /**
     * Main method that initializes the {@link DatabaseHelper}, runs the automated tests,
     * prints the test report, and closes the database connection.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("______________________________________");
        System.out.println("\nDatabase Helper Testing Automation");
        System.out.println("______________________________________\n");
        
        DatabaseHelper dbHelper = new DatabaseHelper();
        try {
            dbHelper.connectToDatabase();
            System.out.println("Database connection established.\n");
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            return;
        }
        
        // Run automated test cases
        testRegisterAndLogin(dbHelper);
        testDoesUserExist(dbHelper);
        testInvitationCode(dbHelper);
        testPasswordResetWorkflow(dbHelper);
        testQuestionAnswerOperations(dbHelper);
        
        // Test summary report
        System.out.println("____________________________________________________________________________");
        System.out.println("\nTotal tests passed: " + numPassed);
        System.out.println("Total tests failed: " + numFailed + "\n");
        
        dbHelper.closeConnection();
    }

    /**
     * Test 1: Register a new user and validate the login functionality.
     * <p>
     * This test registers a user (if not already registered) and then uses the {@code login}
     * method to ensure that the user can successfully log in with the provided credentials.
     *
     * @param dbHelper the {@link DatabaseHelper} instance used for database operations
     */
    private static void testRegisterAndLogin(DatabaseHelper dbHelper) {
        System.out.println("____________________________________________________________________________");
        System.out.println("\nTest 1: Register and Login User");
        System.out.println("________________________________\n");
        String username = "testUser1";
        String password = "password123";
        String role = "student";
        User user = new User(username, password, role);
        try {
            if (!dbHelper.doesUserExist(username)) {
                dbHelper.register(user);
                System.out.println("User '" + username + "' registered successfully.");
            } else {
                System.out.println("User '" + username + "' already exists.");
            }
            boolean loginResult = dbHelper.login(user);
            System.out.println("Login result for '" + username + "': " + loginResult);
            if (loginResult) {
                System.out.println("***Success***: User login succeeded as expected.\n");
                numPassed++;
            } else {
                System.out.println("***Failure***: User login failed unexpectedly.\n");
                numFailed++;
            }
        } catch (SQLException e) {
            System.out.println("***Failure***: Exception during Test 1: " + e.getMessage() + "\n");
            numFailed++;
        }
    }

    /**
     * Test 2: Verify that a user exists in the database after registration.
     * <p>
     * This test registers a new user (if necessary) and checks that the
     * {@code doesUserExist} method returns true for that user.
     *
     * @param dbHelper the {@link DatabaseHelper} instance used for database operations
     */
    private static void testDoesUserExist(DatabaseHelper dbHelper) {
        System.out.println("____________________________________________________________________________");
        System.out.println("\nTest 2: Check User Existence");
        System.out.println("________________________________\n");
        String username = "testUser2";
        String password = "pass456";
        String role = "student";
        User user = new User(username, password, role);
        try {
            if (!dbHelper.doesUserExist(username)) {
                dbHelper.register(user);
                System.out.println("User '" + username + "' registered successfully.");
            }
            boolean exists = dbHelper.doesUserExist(username);
            System.out.println("User existence for '" + username + "': " + exists);
            if (exists) {
                System.out.println("***Success***: User existence confirmed.\n");
                numPassed++;
            } else {
                System.out.println("***Failure***: User existence not found.\n");
                numFailed++;
            }
        } catch (SQLException e) {
            System.out.println("***Failure***: Exception during Test 2: " + e.getMessage() + "\n");
            numFailed++;
        }
    }

    /**
     * Test 3: Generate and validate an invitation code.
     * <p>
     * This test generates an invitation code using the {@code generateInvitationCode} method
     * and then validates it with the {@code validateInvitationCode} method. On successful
     * validation, the code is marked as used.
     *
     * @param dbHelper the {@link DatabaseHelper} instance used for database operations
     */
    private static void testInvitationCode(DatabaseHelper dbHelper) {
        System.out.println("____________________________________________________________________________");
        System.out.println("\nTest 3: Invitation Code Generation and Validation");
        System.out.println("________________________________\n");
        try {
            String code = dbHelper.generateInvitationCode();
            System.out.println("Generated invitation code: " + code);
            boolean valid = dbHelper.validateInvitationCode(code);
            System.out.println("Invitation code validation result: " + valid);
            if (valid) {
                System.out.println("***Success***: Invitation code validated and marked as used.\n");
                numPassed++;
            } else {
                System.out.println("***Failure***: Invitation code validation failed.\n");
                numFailed++;
            }
        } catch (Exception e) {
            System.out.println("***Failure***: Exception during Test 3: " + e.getMessage() + "\n");
            numFailed++;
        }
    }

    /**
     * Test 4: Simulate the password reset workflow.
     * <p>
     * This test adds a password reset request, generates a one-time password (OTP),
     * validates the OTP, and then confirms that the OTP is cleared after validation.
     *
     * @param dbHelper the {@link DatabaseHelper} instance used for database operations
     */
    private static void testPasswordResetWorkflow(DatabaseHelper dbHelper) {
        System.out.println("____________________________________________________________________________");
        System.out.println("\nTest 4: Password Reset Workflow");
        System.out.println("________________________________\n");
        String username = "testUser3";
        String password = "initialPass";
        String role = "student";
        User user = new User(username, password, role);
        try {
            if (!dbHelper.doesUserExist(username)) {
                dbHelper.register(user);
                System.out.println("User '" + username + "' registered successfully.");
            }
            // Add a password reset request
            dbHelper.addPasswordResetRequest(username);
            System.out.println("Password reset request added for '" + username + "'.");
            
            // Generate OTP for password reset
            String otp = dbHelper.setOneTimePassword(username);
            System.out.println("Generated OTP: " + otp);
            if (otp == null) {
                System.out.println("***Failure***: OTP generation failed.\n");
                numFailed++;
                return;
            }
            // Validate the OTP
            boolean validOTP = dbHelper.validateOneTimePassword(username, otp);
            System.out.println("OTP validation result: " + validOTP);
            if (validOTP) {
                System.out.println("***Success***: OTP validated and cleared successfully.\n");
                numPassed++;
            } else {
                System.out.println("***Failure***: OTP validation failed.\n");
                numFailed++;
            }
            // Confirm OTP is cleared (subsequent validation should fail)
            boolean validOTPAgain = dbHelper.validateOneTimePassword(username, otp);
            System.out.println("OTP validation on second attempt (expected to fail): " + validOTPAgain);
            if (!validOTPAgain) {
                System.out.println("***Success***: OTP was successfully cleared after validation.\n");
                numPassed++;
            } else {
                System.out.println("***Failure***: OTP still exists after validation.\n");
                numFailed++;
            }
        } catch (SQLException e) {
            System.out.println("***Failure***: Exception during Test 4: " + e.getMessage() + "\n");
            numFailed++;
        }
    }

    /**
     * Test 5: Validate Questions and Answers operations.
     * <p>
     * This test performs a series of operations:
     * <ol>
     *   <li>Adds a new question for a test user.</li>
     *   <li>Updates the question's title and description.</li>
     *   <li>Creates an answer for the question.</li>
     *   <li>Updates the answer's details.</li>
     *   <li>Deletes both the answer and the question.</li>
     * </ol>
     *
     * @param dbHelper the {@link DatabaseHelper} instance used for database operations
     */
    private static void testQuestionAnswerOperations(DatabaseHelper dbHelper) {
        System.out.println("____________________________________________________________________________");
        System.out.println("\nTest 5: Questions and Answers Operations");
        System.out.println("________________________________\n");
        String username = "testUser4";
        String password = "qapass";
        String role = "student";
        User user = new User(username, password, role);
        try {
            if (!dbHelper.doesUserExist(username)) {
                dbHelper.register(user);
                System.out.println("User '" + username + "' registered successfully.");
            }
            // Retrieve user ID
            String userIdStr = dbHelper.getUserId(username);
            if (userIdStr == null) {
                System.out.println("***Failure***: Unable to retrieve user ID for '" + username + "'.\n");
                numFailed++;
                return;
            }
            int userId = Integer.parseInt(userIdStr);
            
            // Add a new question
            String qTitle = "Test Question Title";
            String qDesc = "Test Question Description";
            dbHelper.addQuestion(userId, qTitle, qDesc);
            System.out.println("Question added for '" + username + "'.");
            
            // Retrieve the most recent question ID (assumes auto-increment)
            ResultSet rsQuestions = dbHelper.getAllQuestions();
            int qId = -1;
            while (rsQuestions.next()) {
                qId = rsQuestions.getInt("id");
            }
            if (qId == -1) {
                System.out.println("***Failure***: No question found.\n");
                numFailed++;
                return;
            }
            
            // Update the question
            String newTitle = "Updated Question Title";
            String newDesc = "Updated Question Description";
            dbHelper.updateQuestion(qId, newTitle, newDesc);
            System.out.println("Question with id " + qId + " updated successfully.");
            
            // Create an answer for the question
            String answerFrom = "Test Answerer";
            String answerText = "Test Answer Text";
            dbHelper.createAnswer(qId, userId, answerFrom, answerText);
            System.out.println("Answer created for question id " + qId + ".");
            
            // Retrieve the answer id
            ResultSet rsAnswers = dbHelper.getAnswers(qId);
            int ansId = -1;
            if (rsAnswers.next()) {
                ansId = rsAnswers.getInt("ansId");
            }
            if (ansId == -1) {
                System.out.println("***Failure***: No answer found.\n");
                numFailed++;
                return;
            }
            
            // Update the answer
            String newAnswerFrom = "Updated Answerer";
            String newAnswerText = "Updated Answer Text";
            dbHelper.updateAnswer(ansId, newAnswerFrom, newAnswerText);
            System.out.println("Answer with id " + ansId + " updated successfully.");
            
            // Delete the answer and the question
            dbHelper.deleteAnswer(ansId);
            System.out.println("Answer with id " + ansId + " deleted successfully.");
            dbHelper.deleteQuestion(qId);
            System.out.println("Question with id " + qId + " deleted successfully.\n");
            
            System.out.println("***Success***: Questions and Answers operations test passed.\n");
            numPassed++;
        } catch (SQLException e) {
            System.out.println("***Failure***: Exception during Test 5: " + e.getMessage() + "\n");
            numFailed++;
        }
    }
}