package automation;

import application.UserNameRecognizer;

public class UsernameEvaluatorTesting {

    static int numPassed = 0;
    static int numFailed = 0;

    public static void main(String[] args) {
        System.out.println("______________________________________");
        System.out.println("\nTesting Automation for Username Evaluation");

        /************** Username Tests **************/
        performUsernameTestCase(1, "John_Doe", true);  
        performUsernameTestCase(2, "jd", false);      
        performUsernameTestCase(3, "User1234567890123456789", false); 
        performUsernameTestCase(4, "Invalid@User!", false); 
        performUsernameTestCase(5, "Valid_User", true);  

        System.out.println("____________________________________________________________________________");
        System.out.println("\nNumber of tests passed: " + numPassed);
        System.out.println("Number of tests failed: " + numFailed);
    }

    private static void performUsernameTestCase(int testCase, String inputText, boolean expectedPass) {
        System.out.println("____________________________________________________________________________\n\nTest case: " + testCase);
        System.out.println("Input (Username): \"" + inputText + "\"");
        System.out.println("______________");

        String resultText = UserNameRecognizer.checkForValidUserName(inputText);
        processTestResult(inputText, resultText, expectedPass, "Username");
    }

    private static void processTestResult(String input, String resultText, boolean expectedPass, String testType) {
        if (!resultText.isEmpty()) {
            if (expectedPass) {
                System.out.println("***Failure*** " + testType + " <" + input + "> is invalid but should be valid.");
                numFailed++;
            } else {
                System.out.println("***Success*** " + testType + " <" + input + "> is correctly marked as invalid.");
                numPassed++;
            }
        } else {
            if (expectedPass) {
                System.out.println("***Success*** " + testType + " <" + input + "> is valid!");
                numPassed++;
            } else {
                System.out.println("***Failure*** " + testType + " <" + input + "> should have failed.");
                numFailed++;
            }
        }
    }
}
