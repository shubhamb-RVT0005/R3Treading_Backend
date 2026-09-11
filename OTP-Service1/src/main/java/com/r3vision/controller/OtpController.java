package com.r3vision.controller;

//public class OtpController {
//
//}
//package com.r3vtech.controller;
//
//import com.r3vtech.dto.GenerateOtpRequest;
//import com.r3vtech.dto.OtpResponse;
//import com.r3vtech.dto.VerifyOtpRequest;
//import com.r3vtech.service.OtpService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.r3vision.entity.Dto.GenerateOtpRequest;
import com.r3vision.entity.Dto.OtpResponse;
import com.r3vision.entity.Dto.VerifyOtpRequest;
import com.r3vision.service.OtpService;

@RestController
@RequestMapping("/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    // =========================================================
    // SEND OTP
    // =========================================================

    @PostMapping("/send")
    public ResponseEntity<OtpResponse> sendOtp(
            @Valid @RequestBody GenerateOtpRequest request) {

        OtpResponse response =
                otpService.generateOtp(request);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // VERIFY OTP
    // =========================================================

    @PostMapping("/verify")
    public ResponseEntity<OtpResponse> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        OtpResponse response =
                otpService.verifyOtp(request);

        return ResponseEntity.ok(response);
    }
}