package com.r3vtech.dto;

import com.r3vtech.enums.OtpType;

//public class VerifyOtpRequest {
//
//}
//package com.r3vtech.otp.dto;
//
//import com.r3vtech.otp.enums.OtpType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "OTP type is required")
    private OtpType otpType;

    public VerifyOtpRequest() {
    }

    public VerifyOtpRequest(String userId, String otp, OtpType otpType) {
        this.userId = userId;
        this.otp = otp;
        this.otpType = otpType;
    }

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

    public OtpType getOtpType() {
        return otpType;
    }

    public void setOtpType(OtpType otpType) {
        this.otpType = otpType;
    }
}