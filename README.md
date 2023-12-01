## WebserviceProject
This project is a Spring Boot web service with database integration and sequrity features.
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
## User Credentials
* To log in on the system, use the username and password in the WebserviceProjectApplication-class or change to the username and password to the required. 
* When registred you can now test the API endpoints.
## Technologies and Framework
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL
