package com.r3vtech.dto;

//public class OtpResponse {
//
//}
//package com.r3vtech.otp.dto;
//
public class OtpResponse {

    private boolean success;
    private String message;
    private String userId;
    private String otpType;

    public OtpResponse() {
    }

    public OtpResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public OtpResponse(boolean success, String message, String userId, String otpType) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.otpType = otpType;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtpType() {
        return otpType;
    }

    public void setOtpType(String otpType) {
        this.otpType = otpType;
    }
}