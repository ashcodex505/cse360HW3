# CSE360-SP25

Main repo for CSE 360
-Ashish Kurse's Project 
-This is for Individual HW2
# Q&A CRUD Application

This repository contains a JavaFX application that implements a CRUD (Create, Read, Update, Delete) system for managing questions and answers. The application uses an H2 database with a helper class to perform all database operations.

# ScreenCast Link
  -https://asu.zoom.us/rec/share/_NtpxIsPdIZZ648edWHgWGQ98gv8__sfFQcccTimndC6KI4MIqZj_R5-MS0dE7qv.4661luJU-GILX6aG
  -Passcode: dzWw3eR%
# How to open Javadoc
  - Basically clone this repo and then once you have it locally on your machine open the "docs" folder and then open the index.html in chrome and you will be able to see the Javadoc for my tests
# List of Tests
    1.	Test 1 – Register and Login User:
    Verifies that a new user can be registered and then successfully logged in.
    
    2.	Test 2 – Check User Existence:
    Confirms that after registration, the system correctly identifies the user’s existence in the database.
    
    3.	Test 3 – Invitation Code Generation and Validation:
    Generates an invitation code and validates that it can be used, marking it as used thereafter.
    
    4.	Test 4 – Password Reset Workflow:
    Simulates the password reset process by adding a reset request, generating a one-time password (OTP), validating the OTP, and ensuring that it is cleared after validation.
    
    5.	Test 5 – Questions and Answers Operations:
    Tests the full CRUD operations for questions and answers by adding, updating, and deleting a question and its associated answer.
 # Description of Tests
  Test 1 – Register and Login User:
  This test first checks if a test user exists; if not, it registers the user using the register method from the DatabaseHelper class. Once registered, it attempts to log in using the same credentials via the login method. The test confirms success if the login returns true and prints the status to the console. The test output includes messages confirming whether the user was registered and if the login was successful, providing a clear trace of the authentication process.
  
  Test 2 – Check User Existence:
  This test ensures that once a new user is registered, the doesUserExist method correctly returns true. After registering a user (if needed), the method is invoked to verify the presence of the user in the database. The console output displays the registration status and confirms that the user exists. This test is critical to validate that the registration process successfully writes to the database.
  
  Test 3 – Invitation Code Generation and Validation:
  This test generates an invitation code using the generateInvitationCode method and then validates it with validateInvitationCode. The test checks that the code is correctly recognized as valid and is marked as used immediately upon validation. The console output shows the generated code and the result of its validation, ensuring that the invitation code system functions properly, which is essential for controlled user access.
  
  Test 4 – Password Reset Workflow:
  This test simulates the entire password reset process. It begins by adding a password reset request for a specific user using addPasswordResetRequest. Then, it generates an OTP using setOneTimePassword, followed by validating the OTP via validateOneTimePassword. Finally, it checks that the OTP is cleared from the database after validation, as expected. The console output details each step of the process, making it clear that the reset workflow is working as intended.
  
  Test 5 – Questions and Answers Operations:
  This test covers CRUD operations for the questions and answers features. It starts by adding a new question for a test user with the addQuestion method, then updates the question using updateQuestion. Next, an answer is created using createAnswer, and subsequently updated using updateAnswer. Finally, both the answer and the question are deleted using deleteAnswer and deleteQuestion, respectively. The console output provides a step-by-step trace of these operations, ensuring that the DatabaseHelper handles these functionalities correctly.
# Link to my Source of Inspo
  https://docs.oracle.com/javase/8/docs/api/

# Link to my Javadoc
  file:///Users/ash/eclipse-workspace/cse360_HW2%20/docs/automation/package-summary.html#class-summary



    
    
    
    







