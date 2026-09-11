package com.r3vision.service;

//public class OtpService {
//
//}
//package com.r3vtech.service;
//
//import com.r3vtech.client.NotificationServiceClient;
//import com.r3vtech.dto.EmailRequest;
//import com.r3vtech.dto.GenerateOtpRequest;
//import com.r3vtech.dto.NotificationResponse;
//import com.r3vtech.dto.OtpResponse;
//import com.r3vtech.dto.SmsRequest;
//import com.r3vtech.dto.VerifyOtpRequest;
//import com.r3vtech.entity.Otp;
//import com.r3vtech.entity.OtpType;
//import com.r3vtech.repository.OtpRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.r3vision.client.NotificationServiceClient;
import com.r3vision.entity.Otp;
import com.r3vision.entity.Dto.EmailRequest;
import com.r3vision.entity.Dto.GenerateOtpRequest;
import com.r3vision.entity.Dto.NotificationResponse;
import com.r3vision.entity.Dto.OtpResponse;
import com.r3vision.entity.Dto.SmsRequest;
import com.r3vision.entity.Dto.VerifyOtpRequest;
import com.r3vision.entity.enums.OtpType;
import com.r3vision.repository.OtpRepository;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final NotificationServiceClient notificationServiceClient;

    private final Random random = new Random();

    public OtpService(
            OtpRepository otpRepository,
            NotificationServiceClient notificationServiceClient) {

        this.otpRepository = otpRepository;
        this.notificationServiceClient =
                notificationServiceClient;
    }

    // =========================================================
    // GENERATE OTP
    // =========================================================

    @Transactional
    public OtpResponse generateOtp(
            GenerateOtpRequest request) {

        try {

            // Generate 6 digit OTP
            String otpCode =
                    String.format(
                            "%06d",
                            random.nextInt(1_000_000)
                    );

            // OTP valid for 1 minute
            LocalDateTime expiresAt =
                    LocalDateTime.now().plusMinutes(1);

            // Save OTP
            Otp otp = new Otp();

            otp.setUserId(request.getUserId());
            otp.setOtp(otpCode);
            otp.setRecipient(request.getRecipient());
            otp.setOtpType(request.getOtpType());
            otp.setExpiresAt(expiresAt);
            otp.setVerified(false);

            otpRepository.save(otp);

            // Send notification
            NotificationResponse notificationResponse;

            if (request.getOtpType() == OtpType.EMAIL) {

                EmailRequest emailRequest =
                        new EmailRequest(
                                request.getRecipient(),
                                "R3Treading Email Verification OTP",
                                "Your R3Treading OTP is "
                                        + otpCode
                                        + ". This OTP is valid for 1 minute."
                        );

                notificationResponse =
                        notificationServiceClient
                                .sendEmail(emailRequest);

            } else {

                SmsRequest smsRequest =
                        new SmsRequest(
                                request.getRecipient(),
                                "Your R3Treading OTP is "
                                        + otpCode
                                        + ". Valid for 1 minute."
                        );

                notificationResponse =
                        notificationServiceClient
                                .sendSms(smsRequest);
            }

            // Notification failed
            if (notificationResponse == null
                    || !notificationResponse.isSuccess()) {

                return new OtpResponse(
                        false,
                        false,
                        "OTP generated but notification failed",
                        request.getUserId(),
                        request.getOtpType().name()
                );
            }

            // Notification successful
            return new OtpResponse(
                    true,
                    false,
                    "OTP sent successfully",
                    request.getUserId(),
                    request.getOtpType().name()
            );

        } catch (Exception e) {

            e.printStackTrace();

            return new OtpResponse(
                    false,
                    false,
                    "Failed to generate or send OTP: "
                            + e.getMessage(),
                    request.getUserId(),
                    request.getOtpType().name()
            );
        }
    }

    // =========================================================
    // VERIFY OTP
    // =========================================================

    @Transactional
    public OtpResponse verifyOtp(
            VerifyOtpRequest request) {

        try {

            Otp otp =
                    otpRepository
                            .findTopByUserIdAndOtpTypeAndVerifiedFalseOrderByCreatedAtDesc(
                                    request.getUserId(),
                                    request.getOtpType()
                            )
                            .orElse(null);

            // OTP not found
            if (otp == null) {

                return new OtpResponse(
                        false,
                        false,
                        "OTP not found or already verified",
                        request.getUserId(),
                        request.getOtpType().name()
                );
            }

            // OTP expired
            if (LocalDateTime.now()
                    .isAfter(otp.getExpiresAt())) {

                return new OtpResponse(
                        false,
                        false,
                        "OTP has expired",
                        request.getUserId(),
                        request.getOtpType().name()
                );
            }

            // Wrong OTP
            if (!otp.getOtp()
                    .equals(request.getOtp())) {

                return new OtpResponse(
                        false,
                        false,
                        "Invalid OTP",
                        request.getUserId(),
                        request.getOtpType().name()
                );
            }

            // OTP verified
            otp.setVerified(true);

            otpRepository.save(otp);

            return new OtpResponse(
                    true,
                    true,
                    "OTP verified successfully",
                    request.getUserId(),
                    request.getOtpType().name()
            );

        } catch (Exception e) {

            e.printStackTrace();

            return new OtpResponse(
                    false,
                    false,
                    "OTP verification failed: "
                            + e.getMessage(),
                    request.getUserId(),
                    request.getOtpType().name()
            );
        }
    }
}