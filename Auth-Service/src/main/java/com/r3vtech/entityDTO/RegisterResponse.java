package com.r3vtech.entityDTO;

public class RegisterResponse {

    private String userId;
    private String mobile;
    private String email;
    private String message;

    public RegisterResponse() {
    }

    public RegisterResponse(
            String userId,
            String mobile,
            String email,
            String message) {

        this.userId = userId;
        this.mobile = mobile;
        this.email = email;
        this.message = message;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}