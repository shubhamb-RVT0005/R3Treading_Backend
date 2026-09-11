package com.r3vision.entity.Dto;

//public class SmsRequest {
//
//}
//package com.r3vtech.dto;

public class SmsRequest {

    private String to;
    private String message;

    public SmsRequest() {
    }

    public SmsRequest(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}