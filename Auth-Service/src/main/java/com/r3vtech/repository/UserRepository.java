package com.r3vtech.repository;

import java.util.Optional;

//public interface UserRepository {
//
//}
//package com.r3treading.auth.repository;

//import com.r3treading.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.r3vtech.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    boolean existsByUserId(String userId);
}