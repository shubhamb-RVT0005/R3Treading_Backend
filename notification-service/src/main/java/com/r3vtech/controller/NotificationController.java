package com.r3vtech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//public class NotificationController {
//
//}
//package com.r3vtech.notification.controller;

import com.r3vtech.dto.EmailRequest;
import com.r3vtech.dto.NotificationResponse;
import com.r3vtech.dto.SmsRequest;
import com.r3vtech.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    // =========================================================
    // SEND SMS
    // =========================================================

    @PostMapping("/sms")
    public ResponseEntity<NotificationResponse> sendSms(
            @Valid @RequestBody SmsRequest request) {

        NotificationResponse response =
                notificationService.sendSms(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // =========================================================
    // SEND EMAIL
    // =========================================================

    @PostMapping("/email")
    public ResponseEntity<NotificationResponse> sendEmail(
            @Valid @RequestBody EmailRequest request) {

        NotificationResponse response =
                notificationService.sendEmail(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}