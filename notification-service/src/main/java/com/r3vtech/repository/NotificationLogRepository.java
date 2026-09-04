package com.r3vtech.repository;

//public interface NotificationLogRepository {
//
//}
//package com.r3vtech.notification.repository;
//
//import com.r3vtech.notification.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import com.r3vtech.entity.NotificationLog;

public interface NotificationLogRepository
        extends JpaRepository<NotificationLog, Long> {
}