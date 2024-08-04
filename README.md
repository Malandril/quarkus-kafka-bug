# quarkus-kafka-bug

## Reproducing the bug

Run the application dev mode
```shell script
./gradlew quarkusDev
```

After 5 seconds (the default reply timeout) start the kafka broker with `docker compose up -d`.

When you query the endpoint `/question` `curl -v localhost:8080/question` it fails.

When you query the endpoint `/question-2` `curl -v localhost:8080/question-2` it works as expected.

When you query the endpoint `/answers` `curl -v localhost:8080/answers` it works as expected.


