package com.r3vision.repository;

//public interface OtpRepository {
//
//}
//package com.r3vtech.repository;

//import com.r3vtech.entity.Otp;
//import com.r3vtech.entity.OtpType;

import org.springframework.data.jpa.repository.JpaRepository;

import com.r3vision.entity.Otp;
import com.r3vision.entity.enums.OtpType;

import java.util.Optional;

public interface OtpRepository
        extends JpaRepository<Otp, Long> {

    Optional<Otp> findTopByUserIdAndOtpTypeAndVerifiedFalseOrderByCreatedAtDesc(
            String userId,
            OtpType otpType
    );
}