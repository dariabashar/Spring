Practice 4

REST students API with PostgreSQL, Spring Data JPA, paging and Flyway.

Start the database:

    docker compose up -d

Run the app:

    ./mvnw spring-boot:run

Examples:

    curl "http://localhost:8080/api/students?page=0&size=20"
    curl "http://localhost:8080/api/students/search?name=Aida"
    curl -X POST http://localhost:8080/api/students -H "Content-Type: application/json" -d '{"uniId":"23B030003","fullName":"New Student"}'
