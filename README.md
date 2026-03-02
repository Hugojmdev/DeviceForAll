# Spring Boot CRUD Backend – MySQL

## Overview

This project is a structured backend application built with Java and Spring Boot, using MySQL as the relational database.

It demonstrates a clean and maintainable layered architecture designed for scalable CRUD-based systems, following enterprise best practices.

The goal of this project is to provide a reference implementation of a well-structured REST API backend.

---

## Tech Stack

- Java 21 
- Spring Boot 3.2.x
- Spring Data JPA
- Hibernate
- MySQL 8.x
- Swagger / OpenAPI
- Docker

---

## Architecture

The application follows a standard layered architecture:

Controller → Service → Repository → Database

Key architectural elements:

- DTO pattern for data transfer
- Mapper layer for Entity ↔ DTO conversion
- Global exception handling
- Consistent HTTP response handling
- Constructor-based dependency injection
- Swagger integration for API documentation

---

## Features

- REST API endpoints
- Full CRUD operations
- Global exception handling
- Clean separation of concerns
- Scalable and reusable structure
- Docker configuration for database setup

---

## API Documentation

Swagger UI is available at:

http://localhost:8080/swagger-ui.html

This allows interactive testing and documentation of all REST endpoints.

---

## Example Endpoints

GET    /api/loans  
GET    /api/loans/{id}  
POST   /api/loans  
PUT    /api/loans/{id}  
DELETE /api/loans/{id}

---

## Running the Project

1. Clone the repository
2. Configure `application.properties`
3. Start MySQL (or use Docker)
4. Run the Spring Boot application:

  ` mvn spring-boot:run`

---

## Docker Support

The project includes Docker configuration to simplify local database setup.

You can start the MySQL container using:

`docker-compose up`

---

## Purpose

This project serves as a reference implementation of a clean Spring Boot backend system using MySQL.

It demonstrates structured CRUD development, layered architecture, API documentation with Swagger, and best practices for maintainable backend systems.