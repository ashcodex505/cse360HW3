# CSE360-SP25

Main repo for CSE 360
-Ashish Kurse's Project 
-This is for Individual HW2
# Q&A CRUD Application

This repository contains a JavaFX application that implements a CRUD (Create, Read, Update, Delete) system for managing questions and answers. The application uses an H2 database with a helper class to perform all database operations.

# ScreenCast Link
  -https://asu.zoom.us/rec/share/_NtpxIsPdIZZ648edWHgWGQ98gv8__sfFQcccTimndC6KI4MIqZj_R5-MS0dE7qv.4661luJU-GILX6aG
  -Passcode: dzWw3eR%
# Setup Steps
  -So the way to setup the application is you first want to clone the github repo
  -Then open this in your Eclipse IDE and make sure you have your build paths configured and then run setupCSE360 and create an admin account 
  -Then with the admin account generate an Invitation Code and register a User account as a Student so you can then see the Questions and Answers


## User Stories

### Questions

- **Create a Question**  
  - *As a student, I want to create a new question with a valid title and description so that I can ask/post a question on the platform.*  
  - **Acceptance Criteria:**  
    - The system requires a non-empty title and description (positive outcome).  
    - If either field is empty or contains invalid data, an error message is displayed (negative outcome).  
    - Upon valid input, the new question is stored successfully in the QuestionList.

- **Read/View a Question**  
  - *As a student, I want to view the details of a question (including its title, description, and any associated answers) so that I can understand the context and content of the question.*  
  - **Acceptance Criteria:**  
    - The system displays all relevant details for the selected question.  
    - If the question ID does not exist, an appropriate error message is shown.

- **Update a Question**  
  - *As a student, I want to update my existing question so that I can correct or improve my question's content.*  
  - **Acceptance Criteria:**  
    - The system allows modifications to the title and/or description only if that is the student’s question.  
    - Input validation ensures that the new values are not empty or malformed.  
    - If invalid data is entered, the system provides a clear error message.

- **Delete a Question**  
  - *As a student, I want to delete my question that is no longer relevant or is incorrect.*  
  - **Acceptance Criteria:**  
    - The system prompts for confirmation before deletion.  
    - Upon confirmation, the student’s question is removed from the system.  
    - If the question cannot be found, an error message is displayed.

### Answers

- **Create an Answer**  
  - *As a student, I want to submit an answer to an existing question so that I can contribute a solution or explanation.*  
  - **Acceptance Criteria:**  
    - The answer must include non-empty content and be associated with a valid question.  
    - If the answer content is empty or the question ID is invalid, the system shows an error message.  
    - Upon valid input, the answer is stored and linked to the corresponding question.

- **Read/View an Answer**  
  - *As a student, I want to view an answer in detail along with the question it addresses so that I can fully understand the provided solution.*  
  - **Acceptance Criteria:**  
    - The system displays the answer content along with its related question details.  
    - If the answer is not found, an error message is shown.

- **Update an Answer**  
  - *As a student, I want to update my answer to correct mistakes or provide additional clarity.*  
  - **Acceptance Criteria:**  
    - The system allows modifications to the answer content.  
    - Input validation checks ensure the updated content is valid (e.g., not empty).  
    - If invalid input is detected, a clear error message is provided.

- **Delete an Answer**  
  - *As a student, I want to delete my answer if it is no longer applicable or correct.*  
  - **Acceptance Criteria:**  
    - The system confirms the deletion before proceeding.  
    - Upon confirmation, the answer is removed from the system.  
    - If the answer does not exist, the system returns an appropriate error message.

### List and Search Operations

- **View/List Questions**  
  - *As a student, I want to view a list of all current questions so that I can browse the available topics.*  
  - **Acceptance Criteria:**  
    - The system displays a complete list of questions.  
    - If there are no questions, an appropriate message is shown.

- **View/List Answers**  
  - *As a student, I want to view answers for individual questions.*  
  - **Acceptance Criteria:**  
    - The system displays a list of answers associated with a question.

## Test Cases

### Question CRUD Operations

- **Create Question**  
  - *Test Case 1:*  
    - **Action:** In the UI (PostQuestion), enter a valid title and description; click "Post Question".  
    - **Expected Result:** The question is inserted into the Questions table and appears in the QuestionList.
  - *Test Case 2 (Negative):*  
    - **Action:** Attempt to post a question with an empty title or description.  
    - **Expected Result:** An error message is shown; no insertion occurs.

- **Read Questions**  
  - *Test Case 3:*  
    - **Action:** In QuestionList, call `getAllQuestions()`.  
    - **Expected Result:** The returned list contains all questions from the database with the proper fields (qTitle, qDesc, id, sId).

- **Update Question**  
  - *Test Case 4:*  
    - **Action:** Select a question in the QuestionList, click "Update Selected", modify the title/description in the UpdateQuestion form, and click "Update".  
    - **Expected Result:** The question’s details in the database are updated, and the refreshed QuestionList shows the new data.
  - *Test Case 5 (Negative):*  
    - **Action:** Try updating a question with an empty title/description.  
    - **Expected Result:** An error message is shown; update is rejected.

- **Delete Question**  
  - *Test Case 6:*  
    - **Action:** Select a question in the QuestionList, click "Delete Selected", and confirm deletion.  
    - **Expected Result:** The question is removed from the database and no longer appears in the QuestionList.

### Answer CRUD Operations

- **Create Answer**  
  - *Test Case 1:*  
    - **Action:** In the AnswerList for a given question, click "Post Answer", fill out the form with valid "Answer From" and "Answer Text", and click "Post Answer".  
    - **Expected Result:** A new answer is inserted into the Answers table (associated with the correct question ID) and appears in the AnswerList.
  - *Test Case 2 (Negative):*  
    - **Action:** Try to post an answer with an empty "Answer From" or "Answer Text".  
    - **Expected Result:** An error message is displayed; no insertion occurs.

- **Read Answers**  
  - *Test Case 3:*  
    - **Action:** Open AnswerList for a specific question (qId).  
    - **Expected Result:** Only the answers with that qId are retrieved and displayed.

- **Update Answer**  
  - *Test Case 4:*  
    - **Action:** In the AnswerList, select an answer, click "Update Selected" (via the update-answer UI), change the fields, and click "Update".  
    - **Expected Result:** The answer in the database is updated; the refreshed AnswerList displays the new details.
  - *Test Case 5 (Negative):*  
    - **Action:** Try updating an answer with empty fields.  
    - **Expected Result:** An error message is shown; update is rejected.

- **Delete Answer**  
  - *Test Case 6:*  
    - **Action:** In the AnswerList, select an answer, click "Delete Selected", and confirm deletion.  
    - **Expected Result:** The answer is deleted from the database and removed from the list.

# Here are the respective UML diagrams as well as sequence diagrams
<img width="510" alt="image" src="https://github.com/user-attachments/assets/efac4f6f-3a07-4236-a8a0-80b25de2b54e" />
<img width="525" alt="image" src="https://github.com/user-attachments/assets/e2d00e1e-b531-45ab-99e8-089f499d8328" />
<img width="507" alt="image" src="https://github.com/user-attachments/assets/2117129f-38dd-476e-8f8f-3f62248408bd" />



