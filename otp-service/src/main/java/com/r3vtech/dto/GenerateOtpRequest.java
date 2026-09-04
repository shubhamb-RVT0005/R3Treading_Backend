package com.r3vtech.dto;

import com.r3vtech.enums.OtpType;

//public class GenerateOtpRequest {
//
//}
//package com.r3vtech.otp.dto;

//import com.r3vtech.otp.enums.OtpType;
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

    public GenerateOtpRequest(String userId, String destination, OtpType otpType) {
        this.userId = userId;
        this.destination = destination;
        this.otpType = otpType;
    }

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