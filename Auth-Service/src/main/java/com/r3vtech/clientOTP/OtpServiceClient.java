package com.r3vtech.clientOTP;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.r3vtech.entityDTO.ApiResponse;
import com.r3vtech.entityDTO.GenerateOtpRequest;
import com.r3vtech.entityDTO.VerifyOtpRequest;

@FeignClient(name = "otp-service")
public interface OtpServiceClient {

    @PostMapping("/otp/generate/mobile")
    com.r3vtech.entityDTO.ApiResponse<?> generateMobileOtp(
            @RequestBody com.r3vtech.entityDTO.GenerateOtpRequest request
    );

    @PostMapping("/otp/generate/email")
    ApiResponse<?> generateEmailOtp(
            @RequestBody com.r3vtech.entityDTO.GenerateOtpRequest request
    );

    @PostMapping("/otp/verify/mobile")
    ApiResponse<?> verifyMobileOtp(
            @RequestBody VerifyOtpRequest request
    );

    @PostMapping("/otp/verify/email")
    ApiResponse<?> verifyEmailOtp(
            @RequestBody VerifyOtpRequest request
    );

    @PostMapping("/otp/resend/mobile")
    ApiResponse<?> resendMobileOtp(
            @RequestBody GenerateOtpRequest request
    );

    @PostMapping("/otp/resend/email")
    ApiResponse<?> resendEmailOtp(
            @RequestBody GenerateOtpRequest request
    );
}