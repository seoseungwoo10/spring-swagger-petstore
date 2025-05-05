# Petstore API with gRPC & JSON (MVP)

**Version:** 1.0
**Date:** 2025-04-29

## 1. Introduction

This project is a Minimum Viable Product (MVP) demonstrating a simple Petstore API implemented using Spring Boot 3.3.x. Its primary goal is to serve as a learning tool for developers familiar with REST/JSON APIs to understand the fundamentals of gRPC by providing parallel implementations of basic API endpoints.

Developers can directly compare the request/response structures and invocation methods between REST/JSON and gRPC for the same operations (`AddPet`, `GetPetById`).

## 2. Technology Stack

*   Spring Boot 3.3.x (Java 17)
*   Maven
*   `spring-boot-starter-web` (for REST/JSON)
*   `grpc-spring-boot-starter` (for gRPC)
*   In-memory data storage (`java.util.Map`)

## 3. Building and Running

1.  **Prerequisites:**
    *   Java 17 or later
    *   Maven
    *   `curl` (for testing REST)
    *   `grpcurl` (for testing gRPC - installation instructions: [https://github.com/fullstorydev/grpcurl](https://github.com/fullstorydev/grpcurl))

2.  **Compile the project:** This step also generates the necessary gRPC Java code from the `.proto` file.
    ```bash
    mvn compile
    ```

3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
    The application will start, with the REST API available on port 8080 and the gRPC API on port 9090 (default for `grpc-spring-boot-starter`).

## 4. API Usage Examples

### 4.1. REST API (using `curl`)

*   **Add a Pet:**
    ```bash
    curl -X POST http://localhost:8080/pets -H "Content-Type: application/json" -d '{"name": "Buddy", "status": "available"}'
    ```
    *Expected Response (ID may vary):*
    ```json
    {"id":1,"name":"Buddy","status":"available"}
    ```

*   **Get Pet by ID (e.g., ID 1):**
    ```bash
    curl http://localhost:8080/pets/1
    ```
    *Expected Response (if found):*
    ```json
    {"id":1,"name":"Buddy","status":"available"}
    ```
    *Expected Response (if not found, e.g., ID 99):*
    ```bash
    curl -i http://localhost:8080/pets/99
    # HTTP/1.1 404 Not Found
    # ... headers ...
    # {"timestamp":"...","status":404,"error":"Not Found","message":"Pet not found with ID: 99","path":"/pets/99"}
    ```

### 4.2. gRPC API (using `grpcurl`)

*   **List Services:** (Verify the service is registered)
    ```bash
    grpcurl -plaintext localhost:9090 list
    ```
    *Expected Output:*
    ```
    grpc.health.v1.Health
    grpc.reflection.v1alpha.ServerReflection
    petstore.PetService
    ```

*   **Describe PetService:**
    ```bash
    grpcurl -plaintext localhost:9090 describe petstore.PetService
    ```

*   **Add a Pet:**
    ```bash
    grpcurl -plaintext -d '{"name": "Lucy", "status": "pending"}' localhost:9090 petstore.PetService/AddPet
    ```
    *Expected Response (ID may vary):*
    ```json
    {
      "id": "2",
      "name": "Lucy",
      "status": "pending"
    }
    ```

*   **Get Pet by ID (e.g., ID 2):**
    ```bash
    grpcurl -plaintext -d '{"petId": 2}' localhost:9090 petstore.PetService/GetPetById
    ```
    *Expected Response (if found):*
    ```json
    {
      "id": "2",
      "name": "Lucy",
      "status": "pending"
    }
    ```

*   **Get Pet by ID (e.g., ID 99 - Not Found):**
    ```bash
    grpcurl -plaintext -d '{"petId": 99}' localhost:9090 petstore.PetService/GetPetById
    ```
    *Expected Output (Error):*
    ```
    ERROR:
      Code: NotFound
      Message: Pet not found with ID: 99
    ```

## 5. Data Structure Comparison (Pet)

*   **JSON (REST):**
    ```json
    {
      "id": 1,
      "name": "Buddy",
      "status": "available"
    }
    ```

*   **Protobuf Message (`petstore.proto`):**
    ```protobuf
    message Pet {
      int64 id = 1;
      string name = 2;
      string status = 3;
    }
    ```
    Note the explicit field numbers (`= 1`, `= 2`, `= 3`) and types (`int64`, `string`) required by Protobuf for efficient serialization and schema evolution.

* modify
