package com.SonuYadav.Linkedin.notification_service.repository;

import com.SonuYadav.Linkedin.notification_service.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
