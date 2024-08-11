# Client Organisations and Personnel Administration Portal

This project is a web-based administration portal for managing client organisations and personnel. The backend is built using Java and SpringBoot, with a basic frontend using HTML, CSS, and jQuery. The portal allows you to view, create, edit, and delete records for client organisations and personnel.

## Features

- **Client Organisations Management**
    - View all client organisations.
    - Create, edit, and delete client organisations.
    - Automatically disable organisations whose expiry date has passed.
    - Display a warning for organisations whose expiry date is within 7 days.

- **Personnel Management**
    - View all personnel associated with a client organisation.
    - Create, edit, and delete personnel records.

## Technologies Used

- **Backend**: Java, Spring Boot, Spring Data JPA, RESTful APIs
- **Frontend**: HTML, CSS, jQuery
- **Database**: H2 (In-memory database for development and testing)

## Prerequisites

- Java 17 or later
- Maven 3.6 or later

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/niranjankumar048/AdminPortalDemo
cd AdminPortalDemo
```
### 2. Build the project
Use Maven to build the project:
```bash
mvn clean install
```
### 3. Running the Application
To run the application, use the following command:
```bash
mvn spring-boot:run
```
### 4. Accessing the Frontend
The frontend of the application is accessible via a web browser. Open your browser and navigate to:
```bash
http://localhost:8080/index.html
```
### 5. Using the Application
Home Page: Displays the count of enabled client organisations and warnings for organisations with expiry dates within 7 days.
Client Organisations: Manage client organisations by viewing, creating, editing, and deleting them.
Personnel: Manage personnel associated with client organisations.
### 6. API Endpoints
The backend provides the following RESTful API endpoints as below:

Client Organisations

GET /api/client-organisations: Get all client organisations.

POST /api/client-organisations: Create a new client organisation.

PUT /api/client-organisations/{id}: Update an existing client organisation.

DELETE /api/client-organisations/{id}: Delete a client organisation.

Personnel

GET /api/personnel: Get all personnel.

POST /api/personnel: Create a new personnel record.

PUT /api/personnel/{id}: Update an existing personnel record.

DELETE /api/personnel/{id}: Delete a personnel record.

### 7. Testing
You can run the tests using Maven:
```bash
mvn test
```
### 8. Database
The application uses an in-memory H2 database by default, which resets every time the application restarts. For persistent storage, you can configure a different database in the application.properties file.

### 9. Configuration
All configurations are located in the src/main/resources/application.properties file. You can adjust database settings, server ports, and other configurations as needed.

Troubleshooting
If you encounter a Whitelabel Error Page, make sure you have correctly mapped the frontend files in your Spring Boot application and that the server is running.
For issues related to database connectivity, check the database configurations in application.properties file.
