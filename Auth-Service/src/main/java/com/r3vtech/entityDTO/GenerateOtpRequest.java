package com.r3vtech.entityDTO;

import com.r3vtech.enums.OtpType;

//public class GenerateOtpRequest {
//
//}
//package com.r3treading.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GenerateOtpRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "OTP type is required")
    private OtpType otpType;


    public GenerateOtpRequest() {
    }


    public GenerateOtpRequest(
            String userId,
            String destination,
            OtpType otpType) {

        this.userId = userId;
        this.destination = destination;
        this.otpType = otpType;
    }


    // =========================
    // Getters and Setters
    // =========================

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public OtpType getOtpType() {
        return otpType;
    }

    public void setOtpType(OtpType otpType) {
        this.otpType = otpType;
    }
}



//
//
//Example mobile OTP request
//{
//    "userId": "USR-A1234567",
//    "destination": "9876543210",
//    "otpType": "MOBILE"
//}
//Example email OTP request
//{
//    "userId": "USR-A1234567",
//    "destination": "shubham@gmail.com",
//    "otpType": "EMAIL"
//}
//Your flow
//Register
//   ↓
//Auth Service
//   ↓
//Create User
//   ↓
//PENDING_VERIFICATION
//   ↓
//OtpServiceClient
//   ↓
//OTP Service
//   ↓
//Generate 6-digit OTP
//   ↓
//Expiry = 1 minute
//   ↓
//Notification Service
//   ├── SMS → Mobile
//   └── Email → Email
//
//One more thing: do not use com.r3vtech.entityDTO for this project if your Auth Service package is com.r3treading.auth. Keep the package structure consistent:
//
//com.r3treading.auth
//├── controller
//├── service
//├── repository
//├── entity
//├── dto
//├── client
//├── security
//└── enums