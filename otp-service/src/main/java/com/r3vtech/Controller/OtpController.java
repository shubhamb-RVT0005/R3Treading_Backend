package com.r3vtech.Controller;

//public class OtpController {
//
//}
//package com.r3vtech.otp.controller;
//
//import com.r3vtech.otp.dto.GenerateOtpRequest;
//import com.r3vtech.otp.dto.OtpResponse;
//import com.r3vtech.otp.dto.VerifyOtpRequest;
//import com.r3vtech.otp.service.OtpService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.r3vtech.dto.GenerateOtpRequest;
import com.r3vtech.dto.OtpResponse;
import com.r3vtech.dto.VerifyOtpRequest;
import com.r3vtech.service.OtpService;

@RestController
@RequestMapping("/otp")
@CrossOrigin(origins = "*")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    // =========================================================
    // GENERATE MOBILE OTP
    // =========================================================

    @PostMapping("/generate/mobile")
    public ResponseEntity<OtpResponse> generateMobileOtp(
            @Valid @RequestBody GenerateOtpRequest request) {

        OtpResponse response = otpService.generateMobileOtp(request);

        if (response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // =========================================================
    // GENERATE EMAIL OTP
    // =========================================================

    @PostMapping("/generate/email")
    public ResponseEntity<OtpResponse> generateEmailOtp(
            @Valid @RequestBody GenerateOtpRequest request) {

        OtpResponse response = otpService.generateEmailOtp(request);

        if (response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // =========================================================
    // VERIFY MOBILE OTP
    // =========================================================

    @PostMapping("/verify/mobile")
    public ResponseEntity<OtpResponse> verifyMobileOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        OtpResponse response = otpService.verifyMobileOtp(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // =========================================================
    // VERIFY EMAIL OTP
    // =========================================================

    @PostMapping("/verify/email")
    public ResponseEntity<OtpResponse> verifyEmailOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        OtpResponse response = otpService.verifyEmailOtp(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // =========================================================
    // RESEND MOBILE OTP
    // =========================================================

    @PostMapping("/resend/mobile")
    public ResponseEntity<OtpResponse> resendMobileOtp(
            @Valid @RequestBody GenerateOtpRequest request) {

        OtpResponse response = otpService.resendMobileOtp(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // =========================================================
    // RESEND EMAIL OTP
    // =========================================================

    @PostMapping("/resend/email")
    public ResponseEntity<OtpResponse> resendEmailOtp(
            @Valid @RequestBody GenerateOtpRequest request) {

        OtpResponse response = otpService.resendEmailOtp(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}