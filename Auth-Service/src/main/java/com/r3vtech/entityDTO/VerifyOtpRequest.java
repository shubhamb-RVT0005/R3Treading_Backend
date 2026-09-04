package com.r3vtech.entityDTO;

//public class VerifyOtpRequest {
//
//}
//package com.r3treading.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class VerifyOtpRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "OTP is required")
    @Pattern(
        regexp = "^[0-9]{6}$",
        message = "OTP must be exactly 6 digits"
    )
    private String otp;


    // =========================
    // Getters and Setters
    // =========================

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}


//
//This will be used for:
//
//POST /auth/verify-mobile
//
//and:
//
//POST /auth/verify-email
//
//Example:
//
//{
//    "userId": "USR-A1234567",
//    "otp": "482931"
//}