package com.r3vtech.Controller;

//import com.r3vtech.entityDTO.UserProfileResponse;

//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//public class AuthController {
//
//}
//package com.r3vtech.controller;

import com.r3vtech.entityDTO.ApiResponse;
import com.r3vtech.entityDTO.LoginRequest;
import com.r3vtech.entityDTO.LoginResponse;
import com.r3vtech.entityDTO.RegisterRequest;
import com.r3vtech.entityDTO.RegisterResponse;
import com.r3vtech.entityDTO.UserProfileResponse;
import com.r3vtech.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // =====================================================
    // REGISTER
    // =====================================================

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        ApiResponse<RegisterResponse> response =
                authService.register(request);

        if (!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUser(
            Authentication authentication) {

        String userId = authentication.getName();


        ApiResponse<UserProfileResponse> response =
                authService.getCurrentUser(userId);


        if (!response.isSuccess()) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }


        return ResponseEntity.ok(response);
    }

    // =====================================================
    // VERIFY MOBILE OTP
    // =====================================================

//    @PostMapping("/verify-mobile")
//    public ResponseEntity<ApiResponse<?>> verifyMobileOtp(
//            @Valid @RequestBody VerifyOtpRequest request) {
//
//        ApiResponse<?> response =
//                authService.verifyMobileOtp(request);
//
//        if (!response.isSuccess()) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(response);
//        }
//
//        return ResponseEntity.ok(response);
//    }


    // =====================================================
    // VERIFY EMAIL OTP
    // =====================================================

//    @PostMapping("/verify-email")
//    public ResponseEntity<ApiResponse<?>> verifyEmailOtp(
//            @Valid @RequestBody VerifyOtpRequest request) {
//
//        ApiResponse<?> response =
//                authService.verifyEmailOtp(request);
//
//        if (!response.isSuccess()) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(response);
//        }
//
//        return ResponseEntity.ok(response);
//    }


    // =====================================================
    // RESEND MOBILE OTP
    // =====================================================

//    @PostMapping("/resend-mobile-otp/{userId}")
//    public ResponseEntity<ApiResponse<?>> resendMobileOtp(
//            @PathVariable String userId) {
//
//        ApiResponse<?> response =
//                authService.resendMobileOtp(userId);
//
//        if (!response.isSuccess()) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(response);
//        }
//
//        return ResponseEntity.ok(response);
//    }


    // =====================================================
    // RESEND EMAIL OTP
    // =====================================================

//    @PostMapping("/resend-email-otp/{userId}")
//    public ResponseEntity<ApiResponse<?>> resendEmailOtp(
//            @PathVariable String userId) {
//
//        ApiResponse<?> response =
//                authService.resendEmailOtp(userId);
//
//        if (!response.isSuccess()) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(response);
//        }
//
//        return ResponseEntity.ok(response);
//    }


    // =====================================================
    // LOGIN
    // =====================================================

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        ApiResponse<LoginResponse> response =
                authService.login(request);

        if (!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }
}