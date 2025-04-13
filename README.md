# Book Management System Service
## Technologies:
#### Java 21
### maven 3.9.5
### swagger 2.5.0
### MYSQL 8.0.41
## Step 1: Creating Database and Table Script on MYSQL:
####  CREATE DATABASE `bookmanagementsystem_db`;
#### CREATE TABLE `book` (
#### `id` bigint NOT NULL AUTO_INCREMENT,
#### `created_at` datetime(6) NOT NULL,
#### `updated_at` datetime(6) NOT NULL,
#### `author` varchar(50) DEFAULT NULL,
#### `isbn` varchar(13) DEFAULT NULL,
#### `title` varchar(100) DEFAULT NULL,
#### PRIMARY KEY (`id`)
#### ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
## Step 2: Checkout the Code From GitHub:
#### git clone https://github.com/mvmaile/bookmanagementsystem-api.git
## Step 3: Configurations:
### Change below on platform-bookstore.properties file to your db connections 
### spring.datasource.url=jdbc:mysql://your host:your mysql port/bookmanagementsystem_db
### spring.datasource.username =your mysql username
### spring.datasource.password =your mysql password
## Step 4: Running on Local Machine 
### cd /bookstore-service$ run below command
#### $mvn clean install -DskipTests
#### $mvn spring-boot:run
### When the app is running access API through Swagger url below:
### http://localhost:8084/bookservice/swagger-ui.html
## Step 5: Running the Application in Docker
#### $mvn clean install -DskipTests
#### $docker compose build
#### $docker compose up -d book-service
### When the app is running access API through Swagger url below:
### http://localhost:8084/bookservice/swagger-ui.html

## Step 6 : Running JUnit Test
### For controller layer
#### $mvn test -Dtest=BookControllerTest
### For service layer
#### $mvn test -Dtest=BookServiceTest
### For both layer
#### $mvn test


