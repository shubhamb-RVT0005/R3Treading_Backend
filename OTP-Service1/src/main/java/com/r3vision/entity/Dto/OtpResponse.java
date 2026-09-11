package com.r3vision.entity.Dto;

//public class OtpResponse {
//
//}
//package com.r3vtech.dto;

public class OtpResponse {

    private boolean success;
    private boolean verified;
    private String message;
    private String userId;
    private String otpType;

    public OtpResponse() {
    }

    public OtpResponse(
            boolean success,
            boolean verified,
            String message) {

        this.success = success;
        this.verified = verified;
        this.message = message;
    }

    public OtpResponse(
            boolean success,
            boolean verified,
            String message,
            String userId,
            String otpType) {

        this.success = success;
        this.verified = verified;
        this.message = message;
        this.userId = userId;
        this.otpType = otpType;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getOtpType() {
        return otpType;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOtpType(String otpType) {
        this.otpType = otpType;
    }
}