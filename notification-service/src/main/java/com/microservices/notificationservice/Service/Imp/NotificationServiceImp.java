package com.microservices.notificationservice.Service.Imp;

import com.microservices.notificationservice.Entity.NotificationEntity;

import java.util.List;
import java.util.Optional;

public interface NotificationServiceImp {
    List<NotificationEntity> getAllNotifications();
    Optional<NotificationEntity> getNotificationById(int id);
    NotificationEntity saveNotification(NotificationEntity notification);
    void deleteNotificationById(int id);
}
