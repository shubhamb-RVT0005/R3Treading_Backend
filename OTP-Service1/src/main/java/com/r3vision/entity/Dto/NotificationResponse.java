package com.r3vision.entity.Dto;

//public class NotificationResponse {
//
//}
//package com.r3vtech.dto;

public class NotificationResponse {

    private boolean success;
    private String message;

    public NotificationResponse() {
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
}