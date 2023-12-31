## WebserviceProject
This project is a Spring Boot web service with database integration and security features.
### Installation
Follow these steps to install and run the project locally:
### 1. Prerequisites
* Ensure you have Java 17 installed on your computer.
* Verify that Maven is installed: Maven Installation Guide
* You should have a MySQL database available. Make sure you have created a database named 'webservice' and configured the username and password in 'application.properties'.
### 2. Clone the Project
* Clone the project from the GitHub repository: 
* https://github.com/Mohabo93/WebserviceProject
### 3. Configuration
* Open 'src/main/resources/application.properties' and check the settings for your MySQL database. Update if necessary.
### 4. Build and Run
* Navigate to the project's root directory and run: 
* Start the application in WebserviceProjectApplication class
* The application will be accessible at http://localhost:8080.
### 5. Test the API
* You can use tools like Postman to test the various API endpoints. The API is protected with OAuth2 authentication, so you need to generate a JWT token to make requests.
## User Authentication
* To log in on the system, follow these steps: 
* Open the 'WebserviceProjectApplication'-class.
* Find the default username and password provided in the class.
* Use the default credentials or update them as needed.
* Use the provided registration API endpoint to create a new User.
* Once registered or logged in, you can now test the various API endpoints.
* Encure that you include the necessary authentication, such as a JWT token, when making requests.
## Technologies and Framework
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL
