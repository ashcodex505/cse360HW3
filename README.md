# CSE360-SP25

Main repo for CSE 360
-Ashish Kurse's Project 
ASU ID: 1227900923
-This is for Individual HW2
# Setup Steps
  -So the way to setup the application is you first want to clone the github repo
  -Then open this in your Eclipse IDE and make sure you have your build paths configured and then run setupCSE360 and create an admin account 
  -Then with the admin account generate an Invitation Code and register a User account as a Student so you can then see the Questions and Answers
# User Stories
  -	Create a Question 
o	-As a student I want to create a new question with a valid title and description so that I can ask/post a question on the platform 
o	Acceptance Criteria 
	The system requires a non-empty title and description (positive outcome) 
	If either field is empty or contains invalid data, an error message is displayed (negative outcome) 
	Upon valid input the new question is stored successfully in questionList 
-	Read/View a Question  
o	As a student I want to view the details of a question (including its title, description, and any associated answers) so that I can understand the context and content of the question.
o	Acceptance Criteria 
	The system displays all relevant details for the selected questions 
	If the question id does not exist an appropriate error message is shown 
-	Update a Question  
o	As a student , I want to update my existing question so that I can correct or improve my questions content. 
o	Acceptance Criteria 
	The system allows modifications to the title and/or description only if that is the student’s question.
	Input validation ensures that the new values are not empty or malformed 
	If invalid data is entered, system provides clear error message 
-	Delete a Question
o	As a student, I want to delete my question that is no longer relevant or is incorrect.
o	Acceptance Criteria:
	The system prompts for confirmation before deletion.
	Upon confirmation, the student’s question is removed from the system.
	If the question cannot be found, an error message is displayed.
-	Create an Answer
o	As a student, I want to submit an answer to an existing question so that I can contribute a solution or explanation.
o	Acceptance Criteria:
	The answer must include non-empty content and be associated with a valid question.
	If the answer content is empty or the question ID is invalid, the system shows an error message.
	Upon valid input, the answer is stored and linked to the corresponding question.
-	Read/View an Answer
o	As a student, I want to view an answer in detail along with the question it addresses so that I can fully understand the provided solution.
o	Acceptance Criteria:
	The system displays the answer content along with its related question details.
	If the answer is not found, an error message is shown.
-	Update an Answer
o	As a student, I want to update my answer to correct mistakes or provide additional clarity.
o	Acceptance Criteria:
	The system allows modifications to the answer content.
	Input validation checks ensure the updated content is valid (e.g., not empty).
	If invalid input is detected, a clear error message is provided.
-	Delete an Answer
o	As a student, I want to delete my answer if it is no longer applicable or correct.
o	Acceptance Criteria:
	The system confirms the deletion before proceeding.
	Upon confirmation, the answer is removed from the system.
	If the answer does not exist, the system returns an appropriate error message.
-	List and Search Operations
o	View/List Questions
	As a student, I want to view a list of all current questions so that I can browse the available topics.
	Acceptance Criteria:
•	The system displays a complete list of questions.
•	If there are no questions, an appropriate message is shown.
o	View/List Answers
	As a student, I want to view answers for individual questions 
	Acceptance Criteria:
•	The system displays a list of answers 
![image](https://github.com/user-attachments/assets/896ba166-44f8-4ef4-a23f-5779c71b35f7)

# Here are the respective UML diagrams as well as sequence diagrams
<img width="510" alt="image" src="https://github.com/user-attachments/assets/efac4f6f-3a07-4236-a8a0-80b25de2b54e" />
<img width="525" alt="image" src="https://github.com/user-attachments/assets/e2d00e1e-b531-45ab-99e8-089f499d8328" />
<img width="507" alt="image" src="https://github.com/user-attachments/assets/2117129f-38dd-476e-8f8f-3f62248408bd" />



