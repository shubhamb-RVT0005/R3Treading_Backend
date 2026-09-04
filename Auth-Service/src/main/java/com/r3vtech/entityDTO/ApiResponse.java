package com.r3vtech.entityDTO;

//public class ApiResponse {
//
//}
//package com.r3treading.auth.dto;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;


    public ApiResponse() {
    }


    public ApiResponse(
            boolean success,
            String message,
            T data) {

        this.success = success;
        this.message = message;
        this.data = data;
    }


    public ApiResponse(
            boolean success,
            String message) {

        this.success = success;
        this.message = message;
        this.data = null;
    }


    // =========================
    // Getters and Setters
    // =========================

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}



//
//
//
//Registration response
//{
//    "success": true,
//    "message": "Registration initiated. Mobile OTP sent.",
//    "data": {
//        "userId": "USR-A1234567",
//        "mobile": "9876543210",
//        "email": "shubham@gmail.com"
//    }
//}
//Login response
//{
//    "success": true,
//    "message": "Login successful",
//    "data": {
//        "accessToken": "...",
//        "refreshToken": "...",
//        "tokenType": "Bearer",
//        "expiresIn": 3600
//    }
//}
//OTP failure
//{
//    "success": false,
//    "message": "OTP expired",
//    "data": null
//}
//Final DTO structure
//
//You now have:
//
//dto
//│
//├── RegisterRequest.java
//├── RegisterResponse.java
//├── LoginRequest.java
//├── LoginResponse.java
//├── VerifyOtpRequest.java
//└── ApiResponse.java
//
//One important dependency: because RegisterRequest uses jakarta.validation.*, your Auth Service needs the validation starter:
//
//<dependency>
//    <groupId>org.springframework.boot</groupId>
//    <artifactId>spring-boot-starter-validation</artifactId>
//</dependency>
//
//And later in the controller we'll use:
//
//@Valid