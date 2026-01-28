Template project for spring boot application

1. Create schema and destroy previous data after each start
2. Use real postgres instead of H2 to be able to troubleshoot database issues

```shell
./mvnw clean package -DskipTests
docker compose up
curl -v http://localhost:8080/employees
docker compose down
```

Approaches:
1. All methods and classes should be documented
2. Everything should be as simple as possible

Tasks:
1. Amend README.md
2. Add CORS
3. Check N + 1 problem
4. Write logic
5. Write integration tests
6. Write unit tests
7. Database JDBC URL [jdbc:postgresql://localhost:32815/test?loggerLevel=OFF] in IT tests
8. Fix @OneToMany @ManyToOne (Cannot lazily initialize collection of role '' with key '' (no session) in tests)