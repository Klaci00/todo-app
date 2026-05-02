# Todo App

A simple To-Do application built with Spring Boot, Thymeleaf, and H2.
Used as a GitFlow practice project.
[GitFlow Workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)

![Gitflow chart](https://dam-cdn.atl.orangelogic.com/AssetLink/t8b1bnptx6bn40wc43g83j02u5b61064.svg)

Every feature branch is merged into develop, and when develop is ready for release, it's merged into main. This allows for a clear separation of development and production code, and makes it easier to manage releases and hotfixes.  

Every feature branch is accompanied by tests to ensure that new features do not break existing functionality.
## Tech Stack
- Java 21
- Spring Boot 3.5
- Maven
- Thymeleaf
- H2 (in-memory)

## Running locally
mvn spring-boot:run
Then open: http://localhost:8080