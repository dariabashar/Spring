Practice 1

Spring Boot project with a REST endpoint `POST /api/id`, dev/test profiles
and typed configuration (`AppIdProperties`).

Run  :  ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

`IdAuditor` has two implementations, chosen by the property `app.audit.enabled`:
`true` - `LoggingIdAuditor` - logs the length of every accepted ID
`false` - `NoopIdAuditor`    - does nothing

The `dev` profile sets it to `true`, the `test` profile sets it to `false`.

Condition ON:

    ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

Condition OFF:

    ./mvnw spring-boot:run -Dspring-boot.run.profiles=test

Trying:  curl -X POST http://localhost:8080/api/id -H "Content-Type: text/plain" -d "23B030286"