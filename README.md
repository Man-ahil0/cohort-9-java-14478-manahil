# Contact Management System

A full-stack Contact Management System developed using Java Spring Boot and React.js. The application provides secure JWT-based authentication, contact management, validation, pagination, search/filter functionality, automated testing, and SonarQube code-quality analysis.

## Features

### Authentication & Security

- User registration and login
- JWT-based authentication
- Spring Security integration
- Stateless authentication
- Secure password handling
- Handling of malformed JWT tokens
- Protection against leaking internal exception details to clients

### Contact Management

- Create contacts
- View all contacts
- View individual contacts
- Update contacts
- Delete contacts
- Input validation
- Search and filtering
- Pagination

### Backend

- RESTful APIs
- DTO-based request handling
- Exception handling
- Input validation
- Spring Data JPA
- Hibernate
- SQL Server database
- Unit testing with JUnit and Mockito
- Repository testing
- Logging using SLF4J and Logback

### Frontend

- React.js
- Vite
- Bootstrap 5
- React Router
- Axios
- Responsive interface
- Contact listing
- Add contact functionality
- Search/filter functionality
- Pagination
- Login and registration pages

### Code Quality

- SonarQube integration
- Code smell analysis
- Maintainability analysis
- Security analysis
- Automated testing
- CodeRabbit code review
- Isolated datasource for repository tests

---

## Technologies Used

### Backend

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- SQL Server
- Maven
- JUnit
- Mockito
- SLF4J
- Logback

### Frontend

- React.js
- Vite
- Bootstrap 5
- React Router
- Axios

### Development & Testing Tools

- IntelliJ IDEA
- Visual Studio Code
- SQL Server Management Studio
- Postman
- Git
- GitHub
- SonarQube
- CodeRabbit

---

## Project Structure

```text
cohort-9-java-14478-manahil/
│
├── .mvn/
│
├── contact-management-frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── ...
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.cohort_9_java_14478_manahil/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── exception/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │
│   └── test/
│       └── java/
│           └── com.example.cohort_9_java_14478_manahil/
│               ├── controller/
│               ├── exception/
│               ├── repository/
│               ├── security/
│               └── service/
│
├── pom.xml
└── README.md
