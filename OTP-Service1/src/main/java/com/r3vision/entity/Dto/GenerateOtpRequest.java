package com.r3vision.entity.Dto;



import com.r3vision.entity.enums.OtpType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GenerateOtpRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String recipient;

    @NotNull
    private OtpType otpType;

    public GenerateOtpRequest() {
    }

    public GenerateOtpRequest(
            String userId,
            String recipient,
            OtpType otpType) {

        this.userId = userId;
        this.recipient = recipient;
        this.otpType = otpType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public OtpType getOtpType() {
        return otpType;
    }

    public void setOtpType(OtpType otpType) {
        this.otpType = otpType;
    }
}