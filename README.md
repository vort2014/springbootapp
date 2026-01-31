Template project for spring boot application

1. Create schema and destroy previous data after each start
2. Use real postgres instead of H2 to be able to troubleshoot database issues

```shell
./mvnw clean package -DskipTests
docker compose up
curl -v http://localhost:8080/employees
docker compose down
```
