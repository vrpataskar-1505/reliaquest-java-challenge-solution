# Employee Management REST API

## Overview

This project is a Spring Boot REST API that I built as part of the ReliaQuest Entry-Level Java Challenge.
The main idea was to expose employee data through REST endpoints so it can be used by another system.

---

## What I implemented

* API to get all employees
* API to get employee by UUID
* API to create a new employee
* Basic layered structure (Controller → Service)

I kept the implementation simple and focused more on structure and readability.

---

## Tech Stack

* Java 17
* Spring Boot
* Gradle
* JUnit (for testing)

---

## API Endpoints

* `GET /api/v1/employee` → returns all employees
* `GET /api/v1/employee/{uuid}` → returns a specific employee
* `POST /api/v1/employee` → creates a new employee

---

## Approach

* I added a service layer to separate logic from the controller
* Used an in-memory list instead of a database since persistence was not required
* Tried to keep the code clean and simple rather than adding unnecessary complexity

---

## How to Run

Set Java version (if needed):

```bash
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
java -version
```

Run the project:

```bash
.\gradlew.bat bootRun
```

---

## Testing

* Added basic unit tests for service layer
* Added integration tests for controller using MockMvc

Run tests:

```bash
.\gradlew.bat test
```

---

## Future Improvements

* Add database instead of in-memory storage
* Add proper authentication/security
* Add API documentation

---

## Thank you!
