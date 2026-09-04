package com.r3vtech.dto;

//public class SmsRequest {
//
//}
//package com.r3vtech.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SmsRequest {

    @NotBlank(message = "Mobile number is required")
    @Pattern(
        regexp = "^[6-9][0-9]{9}$",
        message = "Enter a valid 10 digit mobile number"
    )
    private String mobile;

    @NotBlank(message = "Message is required")
    private String message;

    public SmsRequest() {
    }

    public SmsRequest(String mobile, String message) {
        this.mobile = mobile;
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}