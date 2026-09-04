package com.r3vtech.service;

//public class NotificationService {
//
//}
//package com.r3vtech.notification.service;

//import com.r3vtech.notification.dto.EmailRequest;
//import com.r3vtech.notification.dto.NotificationResponse;
//import com.r3vtech.notification.dto.SmsRequest;
//import com.r3vtech.notification.entity.NotificationLog;
//import com.r3vtech.notification.enums.NotificationType;
//import com.r3vtech.notification.repository.NotificationLogRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.r3vtech.dto.EmailRequest;
import com.r3vtech.dto.NotificationResponse;
import com.r3vtech.dto.SmsRequest;
import com.r3vtech.entity.NotificationLog;
import com.r3vtech.enums.NotificationType;
import com.r3vtech.repository.NotificationLogRepository;

@Service
public class NotificationService {

    private final NotificationLogRepository notificationLogRepository;

    public NotificationService(
            NotificationLogRepository notificationLogRepository) {
        this.notificationLogRepository = notificationLogRepository;
    }

    // =========================================================
    // SEND SMS
    // =========================================================

    @Transactional
    public NotificationResponse sendSms(SmsRequest request) {

        try {

            // Development mode
            System.out.println("=================================");
            System.out.println("SMS NOTIFICATION");
            System.out.println("Mobile  : " + request.getMobile());
            System.out.println("Message : " + request.getMessage());
            System.out.println("=================================");

            NotificationLog log = new NotificationLog();

            log.setNotificationType(NotificationType.SMS);
            log.setDestination(request.getMobile());
            log.setMessage(request.getMessage());
            log.setStatus("SENT");

            notificationLogRepository.save(log);

            return new NotificationResponse(
                    true,
                    "SMS sent successfully"
            );

        } catch (Exception e) {

            saveFailedLog(
                    NotificationType.SMS,
                    request.getMobile(),
                    null,
                    request.getMessage(),
                    e.getMessage()
            );

            return new NotificationResponse(
                    false,
                    "Failed to send SMS"
            );
        }
    }

    // =========================================================
    // SEND EMAIL
    // =========================================================

    @Transactional
    public NotificationResponse sendEmail(EmailRequest request) {

        try {

            // Development mode
            System.out.println("=================================");
            System.out.println("EMAIL NOTIFICATION");
            System.out.println("Email   : " + request.getEmail());
            System.out.println("Subject : " + request.getSubject());
            System.out.println("Message : " + request.getMessage());
            System.out.println("=================================");

            NotificationLog log = new NotificationLog();

            log.setNotificationType(NotificationType.EMAIL);
            log.setDestination(request.getEmail());
            log.setSubject(request.getSubject());
            log.setMessage(request.getMessage());
            log.setStatus("SENT");

            notificationLogRepository.save(log);

            return new NotificationResponse(
                    true,
                    "Email sent successfully"
            );

        } catch (Exception e) {

            saveFailedLog(
                    NotificationType.EMAIL,
                    request.getEmail(),
                    request.getSubject(),
                    request.getMessage(),
                    e.getMessage()
            );

            return new NotificationResponse(
                    false,
                    "Failed to send email"
            );
        }
    }

    // =========================================================
    // SAVE FAILED NOTIFICATION
    // =========================================================

    private void saveFailedLog(
            NotificationType type,
            String destination,
            String subject,
            String message,
            String errorMessage) {

        NotificationLog log = new NotificationLog();

        log.setNotificationType(type);
        log.setDestination(destination);
        log.setSubject(subject);
        log.setMessage(message);
        log.setStatus("FAILED");
        log.setErrorMessage(errorMessage);

        notificationLogRepository.save(log);
    }
}