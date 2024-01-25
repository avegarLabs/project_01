# MDSN-API

MDSN-API (Musala Dev Social Network) is an API implemented in Spring Boot that features endpoints for creating users and posts, managing relationships between friends or followers. Additionally, it allows users to like or unlike a specific post.
The container with the proposed solution has implemented a mechanism to create basic data for testing the API. This data corresponds to the Users and Posts entities.
It also includes basic documentation to clarify the usage of the endpoints and data types.

## Prerequisites

- Docker
- Docker Compose

## Configuration

Run Docker Compose to build and start the services.

- docker-compose up -d

Then, access the URL http://localhost:8080/swagger-ui/index.html to view the API documentation

If Postman is used to test the API, import the Musala_Test.postman_collection.json collection into the application to use the endpoints.


