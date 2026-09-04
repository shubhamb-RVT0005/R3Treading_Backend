package com.r3vtech.service;

//public class AuthService {
//
//}
//package com.r3vtech.service;

import com.r3vtech.clientOTP.OtpServiceClient;
import com.r3vtech.entity.User;
import com.r3vtech.entityDTO.ApiResponse;
import com.r3vtech.entityDTO.GenerateOtpRequest;
import com.r3vtech.entityDTO.LoginRequest;
import com.r3vtech.entityDTO.LoginResponse;
import com.r3vtech.entityDTO.RegisterRequest;
import com.r3vtech.entityDTO.RegisterResponse;
import com.r3vtech.entityDTO.VerifyOtpRequest;
import com.r3vtech.enums.OtpType;
import com.r3vtech.enums.UserStatus;
import com.r3vtech.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final OtpServiceClient otpServiceClient;


    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            OtpServiceClient otpServiceClient) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.otpServiceClient = otpServiceClient;
    }


    // =====================================================
    // REGISTER
    // =====================================================

    @Transactional
    public ApiResponse<RegisterResponse> register(
            RegisterRequest request) {

        // Check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {

            return new ApiResponse<>(
                    false,
                    "Email is already registered",
                    null
            );
        }


        // Check duplicate mobile
        if (userRepository.existsByMobile(request.getMobile())) {

            return new ApiResponse<>(
                    false,
                    "Mobile number is already registered",
                    null
            );
        }


        // Create User
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setLastName(request.getLastName());
        user.setMobile(request.getMobile());
        user.setEmail(request.getEmail());

        // IMPORTANT:
        // Never store plain password
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );

        user.setTradingPlatformId(
                request.getTradingPlatformId()
        );

        user.setMobileVerified(false);
        user.setEmailVerified(false);

        user.setStatus(
                UserStatus.PENDING_VERIFICATION
        );


        // Save User
        User savedUser = userRepository.save(user);


        // ================================================
        // Generate Mobile OTP
        // ================================================

        GenerateOtpRequest otpRequest =
                new GenerateOtpRequest();

        otpRequest.setUserId(
                savedUser.getUserId()
        );

        otpRequest.setDestination(
                savedUser.getMobile()
        );

        otpRequest.setOtpType(
                OtpType.MOBILE
        );


        try {

            otpServiceClient.generateMobileOtp(
                    otpRequest
            );

        } catch (Exception e) {

            return new ApiResponse<>(
                    false,
                    "User registered but mobile OTP could not be sent",
                    null
            );
        }


        // ================================================
        // Registration Response
        // ================================================

        RegisterResponse response =
                new RegisterResponse(
                        savedUser.getUserId(),
                        savedUser.getMobile(),
                        savedUser.getEmail(),
                        "Registration initiated. Mobile OTP sent."
                );


        return new ApiResponse<>(
                true,
                "Registration initiated. Mobile OTP sent.",
                response
        );
    }


    // =====================================================
    // VERIFY MOBILE OTP
    // =====================================================

    @Transactional
    public ApiResponse<?> verifyMobileOtp(
            VerifyOtpRequest request) {

        User user = userRepository
                .findByUserId(request.getUserId())
                .orElse(null);


        if (user == null) {

            return new ApiResponse<>(
                    false,
                    "User not found",
                    null
            );
        }


        if (user.getMobileVerified()) {

            return new ApiResponse<>(
                    false,
                    "Mobile number is already verified",
                    null
            );
        }


        ApiResponse<?> otpResponse;

        try {

            otpResponse =
                    otpServiceClient.verifyMobileOtp(
                            request
                    );

        } catch (Exception e) {

            return new ApiResponse<>(
                    false,
                    "Unable to verify mobile OTP",
                    null
            );
        }


        if (otpResponse == null
                || !otpResponse.isSuccess()) {

            String message =
                    otpResponse != null
                            ? otpResponse.getMessage()
                            : "Invalid mobile OTP";

            return new ApiResponse<>(
                    false,
                    message,
                    null
            );
        }


        // Mark mobile verified
        user.setMobileVerified(true);

        user.setStatus(
                UserStatus.MOBILE_VERIFIED
        );

        userRepository.save(user);


        // ================================================
        // Generate Email OTP
        // ================================================

        GenerateOtpRequest emailOtpRequest =
                new GenerateOtpRequest();

        emailOtpRequest.setUserId(
                user.getUserId()
        );

        emailOtpRequest.setDestination(
                user.getEmail()
        );

        emailOtpRequest.setOtpType(
                OtpType.EMAIL
        );


        try {

            otpServiceClient.generateEmailOtp(
                    emailOtpRequest
            );

        } catch (Exception e) {

            return new ApiResponse<>(
                    true,
                    "Mobile verified. Email OTP could not be sent.",
                    null
            );
        }


        return new ApiResponse<>(
                true,
                "Mobile verified successfully. Email OTP sent.",
                null
        );
    }


    // =====================================================
    // VERIFY EMAIL OTP
    // =====================================================

    @Transactional
    public ApiResponse<?> verifyEmailOtp(
            VerifyOtpRequest request) {

        User user = userRepository
                .findByUserId(request.getUserId())
                .orElse(null);


        if (user == null) {

            return new ApiResponse<>(
                    false,
                    "User not found",
                    null
            );
        }


        if (!user.getMobileVerified()) {

            return new ApiResponse<>(
                    false,
                    "Mobile number must be verified first",
                    null
            );
        }


        if (user.getEmailVerified()) {

            return new ApiResponse<>(
                    false,
                    "Email is already verified",
                    null
            );
        }


        ApiResponse<?> otpResponse;

        try {

            otpResponse =
                    otpServiceClient.verifyEmailOtp(
                            request
                    );

        } catch (Exception e) {

            return new ApiResponse<>(
                    false,
                    "Unable to verify email OTP",
                    null
            );
        }


        if (otpResponse == null
                || !otpResponse.isSuccess()) {

            String message =
                    otpResponse != null
                            ? otpResponse.getMessage()
                            : "Invalid email OTP";

            return new ApiResponse<>(
                    false,
                    message,
                    null
            );
        }


        // ================================================
        // Mark Email Verified
        // ================================================

        user.setEmailVerified(true);

        // Both verifications completed
        user.setStatus(
                UserStatus.ACTIVE
        );

        userRepository.save(user);


        // ================================================
        // Registration Completed
        // ================================================

        return new ApiResponse<>(
                true,
                "Registration successfully completed",
                null
        );
    }


    // =====================================================
    // RESEND MOBILE OTP
    // =====================================================

    public ApiResponse<?> resendMobileOtp(
            String userId) {

        User user = userRepository
                .findByUserId(userId)
                .orElse(null);


        if (user == null) {

            return new ApiResponse<>(
                    false,
                    "User not found",
                    null
            );
        }


        if (user.getMobileVerified()) {

            return new ApiResponse<>(
                    false,
                    "Mobile number is already verified",
                    null
            );
        }


        GenerateOtpRequest request =
                new GenerateOtpRequest();

        request.setUserId(
                user.getUserId()
        );

        request.setDestination(
                user.getMobile()
        );

        request.setOtpType(
                OtpType.MOBILE
        );


        try {

            otpServiceClient.resendMobileOtp(
                    request
            );

            return new ApiResponse<>(
                    true,
                    "Mobile OTP resent successfully",
                    null
            );

        } catch (Exception e) {

            return new ApiResponse<>(
                    false,
                    "Unable to resend mobile OTP",
                    null
            );
        }
    }


    // =====================================================
    // RESEND EMAIL OTP
    // =====================================================

    public ApiResponse<?> resendEmailOtp(
            String userId) {

        User user = userRepository
                .findByUserId(userId)
                .orElse(null);


        if (user == null) {

            return new ApiResponse<>(
                    false,
                    "User not found",
                    null
            );
        }


        if (!user.getMobileVerified()) {

            return new ApiResponse<>(
                    false,
                    "Mobile number must be verified first",
                    null
            );
        }


        if (user.getEmailVerified()) {

            return new ApiResponse<>(
                    false,
                    "Email is already verified",
                    null
            );
        }


        GenerateOtpRequest request =
                new GenerateOtpRequest();

        request.setUserId(
                user.getUserId()
        );

        request.setDestination(
                user.getEmail()
        );

        request.setOtpType(
                OtpType.EMAIL
        );


        try {

            otpServiceClient.resendEmailOtp(
                    request
            );

            return new ApiResponse<>(
                    true,
                    "Email OTP resent successfully",
                    null
            );

        } catch (Exception e) {

            return new ApiResponse<>(
                    false,
                    "Unable to resend email OTP",
                    null
            );
        }
    }


    // =====================================================
    // LOGIN
    // =====================================================

    public ApiResponse<LoginResponse> login(
            LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElse(null);


        if (user == null) {

            return new ApiResponse<>(
                    false,
                    "Invalid email or password",
                    null
            );
        }


        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash())) {

            return new ApiResponse<>(
                    false,
                    "Invalid email or password",
                    null
            );
        }


        // User must complete verification
        if (!user.getMobileVerified()
                || !user.getEmailVerified()) {

            return new ApiResponse<>(
                    false,
                    "Please complete mobile and email verification",
                    null
            );
        }


        // Check account status
        if (user.getStatus() != UserStatus.ACTIVE) {

            return new ApiResponse<>(
                    false,
                    "User account is not active",
                    null
            );
        }


        // ================================================
        // Generate JWT
        // ================================================

        String accessToken =
                jwtService.generateAccessToken(
                        user.getUserId(),
                        user.getEmail()
                );


        LoginResponse loginResponse =
                new LoginResponse(
                        accessToken,
                        null,
                        "Bearer",
                        jwtService.getAccessTokenExpiration()
                );


        return new ApiResponse<>(
                true,
                "Login successful",
                loginResponse
        );
    }
}