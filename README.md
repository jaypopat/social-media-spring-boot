# Spring Boot Social Media Application

This project consists of a **Spring Boot** backend and a **Vite React** frontend. You can run the application locally using either Maven and npm or Docker.
It provides a simple social media application where users can create posts, like posts, and comment on posts. Users can have their own feed (varies on people they follow) and can follow/unfollow other users.
The project was made to learn spring boot and hence the db is in-memory H2.

## Table of Contents
1. [Getting Started](#getting-started)
2. [Running Locally](#running-locally)
   - [Spring Boot Backend](#spring-boot-backend)
   - [React Frontend](#react-frontend)
3. [Running with Docker](#running-with-docker)
4. [API Documentation](#api-documentation)

## Getting Started

Before running the application, make sure you have the following installed:
- **Java 17** (or higher)
- **Maven** (for the backend)
- **Node.js** (for the frontend)
- **Docker** (optional for running via containers)

## Running Locally

### Spring Boot Backend
To run the backend service, which is a Spring Boot application, follow these steps:

1. Navigate to the `backend` directory:
   ```bash
   cd backend
   ```

2. Start the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```

The backend will start on port `8080`.

### React Frontend
To run the frontend, which is built with Vite and React, follow these steps:

1. Navigate to the `frontend` directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

The frontend will be available on [http://localhost:5173](http://localhost:5173).

### API Documentation
Once the backend is running, you can access the Swagger API documentation at:
[http://localhost:8080/crud/swagger-ui.html](http://localhost:8080/crud/swagger-ui.html)

## Running with Docker

If you prefer using Docker, you can run both the backend and frontend simultaneously with Docker Compose.

1. Make sure Docker is installed and running on your system.

2. From the project root directory, run:
   ```bash
   docker-compose up --build
   ```

This command will build the Docker images for both the Spring Boot backend and the Vite React frontend, and start both services. Once the containers are running:

- The frontend will be available at [http://localhost:5173](http://localhost:5173).
- The backend APIs can be accessed via Swagger UI at [http://localhost:8080/crud/swagger-ui.html](http://localhost:8080/crud/swagger-ui.html).
