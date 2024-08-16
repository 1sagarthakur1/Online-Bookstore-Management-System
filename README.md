# Online Bookstore Management System

## Overview

This project is an Online Bookstore Management System that allows users to browse books, place orders, and manage their accounts. Admin users can manage inventory, view orders, and handle user accounts.

## Features

### User Registration and Authentication
- Users can register with username, password, and email.
- Authentication is managed via Spring Security with JWT tokens.
- Role-based access control for Admin and User roles.

### Book Management (Admin only)
- Admins can add, update, and delete books.
- Each book has fields for title, author, description, price, and quantity.

### Book Browsing and Searching
- Users can view a list of all available books.
- Implemented search functionality allows finding books by title, author, or genre.

### Order Management
- Users can place orders for books.
- Each order includes book details, quantity, total price, and order status.
- Users can view their order history.

### User Profile Management
- Users can view and update their profile information.
- Admins can view all user profiles and perform administrative tasks.

## Technology Stack

- **Backend Framework**: Spring Boot
- **Database**: MySQL
- **API Documentation**: Swagger
- **Security**: Spring Security with JWT
- **Testing**: JUnit and Mockito

## Project Structure

```plaintext
src/
├── main/
│   ├── java/com/bookstore/
│   │   ├── controller/       # Controllers for handling HTTP requests
│   │   ├── model/            # Entity classes representing the database models
│   │   ├── repository/       # Repositories for database access
│   │   ├── service/          # Services containing business logic
│   │   ├── security/         # Security configurations
│   │   ├── config/           # Additional configurations (e.g., Swagger)
│   └── resources/
│       ├── application.properties  # Application configuration file
└── test/
    ├── java/com/bookstore/   # Unit tests
