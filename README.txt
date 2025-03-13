User Management and Authentication API

Overview

This project is a User Management and Authentication API built using Spring Boot. It is designed to provide secure and efficient user registration, authentication, and management functionalities. The API adheres to best practices and follows SOLID principles to ensure maintainability and scalability.

Key Features:

User Registration and Account Verification

Secure Authentication (Login)

Password Reset via Email

User Profile Update and Management

Role-Based Access Control (RBAC) with Permissions

Global Exception Handling for Robust Error Reporting

Standardized API Response Structure

Technologies and Tools

Java 21 - Core programming language.

Spring Boot - Framework for building the REST API.

Spring Security - Handles authentication and authorization.

Lombok - Reduces boilerplate code using annotations.

JWT (JSON Web Token) - For stateless authentication.

Maven - Dependency and build management.

PostgreSQL/MySQL - Database support.

Hibernate/JPA - ORM for database interaction.

Docker - Containerization support.

JUnit and Mockito - Unit and integration testing.

Project Structure

The project follows a layered architecture:

Controller Layer: Handles HTTP requests and responses.

Service Layer: Contains business logic.

Repository Layer: Manages data persistence.

Exception Handling: Global exception handler to manage and return consistent error messages.

Security Configuration: Manages JWT-based authentication and RBAC.

API Endpoints

Authentication and User Registration

POST /api/v1/users/register - Register a new user.

POST /api/v1/users/login - Authenticate and retrieve a JWT token.

GET /api/v1/users/verify-email/{token} - Verify user email with a token.

Password Reset

POST /api/v1/users/password-reset/reset - Reset password using a token.

POST /api/v1/users/password-reset/send-email - Request a password reset link via email.

User Management

PUT /api/v1/users/{id} - Update user profile.

GET /api/v1/users - Retrieve all users (Admin/Manager).

GET /api/v1/users/{id} - Get user details by ID.

GET /api/v1/users/username/{username} - Get user by username.

GET /api/v1/users/email - Get user by email.

GET /api/v1/users/name/{name} - Get user by name.

DELETE /api/v1/users/{id} - Delete a user by ID.

