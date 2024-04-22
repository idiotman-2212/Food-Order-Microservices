package com.microservices.notificationservice.Service;

import com.microservices.notificationservice.Entity.NotificationEntity;
import com.microservices.notificationservice.Repository.NotificationRepository;
import com.microservices.notificationservice.Service.Imp.NotificationServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements NotificationServiceImp {
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public List<NotificationEntity> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<NotificationEntity> getNotificationById(int id) {
        return notificationRepository.findById(id);
    }

    @Override
    public NotificationEntity saveNotification(NotificationEntity notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotificationById(int id) {
        notificationRepository.deleteById(id);
    }
}
