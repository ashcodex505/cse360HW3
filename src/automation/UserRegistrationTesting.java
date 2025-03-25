package automation;

import application.User;
import databasePart1.DatabaseHelper;
import java.sql.SQLException;

public class UserRegistrationTesting {

    static int numPassed = 0;
    static int numFailed = 0;

    public static void main(String[] args) {
        System.out.println("______________________________________");
        System.out.println("\nTesting Automation for User Registration");

        DatabaseHelper databaseHelper = new DatabaseHelper();

        try {
            databaseHelper.connectToDatabase();

            /************** Registration Tests **************/
            performUserRegistrationTest(1, "john_doe", "Password!1", "user", true);  
            performUserRegistrationTest(2, "jane_doe", "SecurePass123!", "admin", true);  
            performUserRegistrationTest(3, "john_doe", "AnotherPass1!", "user", false); 
            performUserRegistrationTest(4, "u", "Short1!", "user", false);  
            performUserRegistrationTest(5, "validUser123", "weak", "user", false);  

            System.out.println("____________________________________________________________________________");
            System.out.println("\nNumber of tests passed: " + numPassed);
            System.out.println("Number of tests failed: " + numFailed);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            databaseHelper.closeConnection();
        }
    }

    private static void performUserRegistrationTest(int testCase, String userName, String password, String role, boolean expectedPass) {
        System.out.println("____________________________________________________________________________\n\nTest case: " + testCase);
        System.out.println("Attempting to register user: " + userName);
        System.out.println("______________");

        DatabaseHelper databaseHelper = new DatabaseHelper();

        try {
            databaseHelper.connectToDatabase();

            if (databaseHelper.doesUserExist(userName)) {
                if (expectedPass) {
                    System.out.println("***Failure*** User <" + userName + "> already exists.");
                    numFailed++;
                } else {
                    System.out.println("***Success*** Duplicate user <" + userName + "> correctly blocked.");
                    numPassed++;
                }
                return;
            }

            User newUser = new User(userName, password, role);
            databaseHelper.register(newUser);

            if (databaseHelper.doesUserExist(userName)) {
                if (expectedPass) {
                    System.out.println("***Success*** User <" + userName + "> registered successfully!");
                    numPassed++;
                } else {
                    System.out.println("***Failure*** User <" + userName + "> should not have been registered.");
                    numFailed++;
                }
            } else {
                System.out.println("***Failure*** User <" + userName + "> was not found in the database after registration.");
                numFailed++;
            }

        } catch (SQLException e) {
            if (expectedPass) {
                System.out.println("***Failure*** SQL Error: " + e.getMessage());
                numFailed++;
            } else {
                System.out.println("***Success*** Registration failed as expected.");
                numPassed++;
            }
        } finally {
            databaseHelper.closeConnection();
        }
    }
}
