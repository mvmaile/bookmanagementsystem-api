# Book Management System Service
## Creating database and table script on MYSQL:
####  CREATE DATABASE `bookmanagementsystem_db`;
#### use `bookmanagementsystem_db`;
#### CREATE TABLE `book` (
#### `id` bigint NOT NULL AUTO_INCREMENT,
#### `created_at` datetime(6) NOT NULL,
#### `updated_at` datetime(6) NOT NULL,
#### `author` varchar(50) DEFAULT NULL,
#### `isbn` varchar(13) DEFAULT NULL,
#### `title` varchar(100) DEFAULT NULL,
#### PRIMARY KEY (`id`)
#### ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
## Step 1: Checkout the code from gitHub:
#### https://gitlab.com/csir-nicis/dirisa/single-sign-on/sso-service.git


## Step 2: cd to the checkout project and run below commands:
# Running on local machine 
### cd /bookstore-service$ run below command
#### $mvn clean install
#### $mvn spring-boot:run
#Running the application in docker
#### $docker compose build
#### $docker compose up -d book-service
#Swagger url
### http://localhost:8084/bookservice/swagger-ui.html


