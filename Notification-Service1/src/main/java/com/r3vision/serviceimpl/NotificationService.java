
package com.r3vision.serviceimpl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.r3vision.dto.EmailRequest;
import com.r3vision.dto.NotificationResponse;
import com.r3vision.entity.NotificationLog;
import com.r3vision.enums.NotificationType;
import com.r3vision.repository.NotificationLogRepository;

@Service
public class NotificationService {

    private final NotificationLogRepository notificationLogRepository;
    private final JavaMailSender mailSender;

    public NotificationService(
            NotificationLogRepository notificationLogRepository,
            JavaMailSender mailSender) {

        this.notificationLogRepository = notificationLogRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    public NotificationResponse sendEmail(
            EmailRequest request) {

        System.out.println("=================================");
        System.out.println("EMAIL NOTIFICATION");
        System.out.println("Email   : " + request.getTo());
        System.out.println("Subject : " + request.getSubject());
        System.out.println("Message : " + request.getMessage());
        System.out.println("=================================");

        try {

            // =================================================
            // 1. Create Email
            // =================================================

            SimpleMailMessage mailMessage =
                    new SimpleMailMessage();

            mailMessage.setTo(request.getTo());
            mailMessage.setSubject(request.getSubject());
            mailMessage.setText(request.getMessage());

            // =================================================
            // 2. Send Email
            // =================================================

            mailSender.send(mailMessage);

            System.out.println(
                    "EMAIL SENT SUCCESSFULLY"
            );

            // =================================================
            // 3. Save SUCCESS Log
            // =================================================

            saveNotificationLog(
                    NotificationType.EMAIL,
                    request.getTo(),
                    request.getSubject(),
                    request.getMessage(),
                    "SENT",
                    null
            );

            // =================================================
            // 4. Response
            // =================================================

            return new NotificationResponse(
                    true,
                    "Email sent successfully"
            );

        } catch (Exception e) {

            System.err.println(
                    "EMAIL SENDING FAILED"
            );

            e.printStackTrace();

            // =================================================
            // Save FAILED Log
            // =================================================

            saveNotificationLog(
                    NotificationType.EMAIL,
                    request.getTo(),
                    request.getSubject(),
                    request.getMessage(),
                    "FAILED",
                    e.getMessage()
            );

            return new NotificationResponse(
                    false,
                    "Failed to send email: "
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // SAVE NOTIFICATION LOG
    // =========================================================

    private void saveNotificationLog(
            NotificationType type,
            String destination,
            String subject,
            String message,
            String status,
            String errorMessage) {

        NotificationLog log =
                new NotificationLog();

        log.setNotificationType(type);
        log.setDestination(destination);
        log.setSubject(subject);
        log.setMessage(message);
        log.setStatus(status);
        log.setErrorMessage(errorMessage);

        notificationLogRepository.save(log);
    }
}
