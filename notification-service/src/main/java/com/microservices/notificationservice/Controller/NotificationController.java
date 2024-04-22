package com.microservices.notificationservice.Controller;

import com.microservices.notificationservice.Entity.NotificationEntity;
import com.microservices.notificationservice.Payload.BaseResponse;
import com.microservices.notificationservice.Payload.PaymentResponse;
import com.microservices.notificationservice.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<?> getAllNotifications() {
        List<NotificationEntity> list = notificationService.getAllNotifications();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(list);
        baseResponse.setMessage("Get all notifications");
        baseResponse.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationById(@PathVariable int id) {
        Optional<NotificationEntity> notification = notificationService.getNotificationById(id);
        return notification.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public ResponseEntity<?> saveNotification(@RequestBody NotificationEntity notification) {
        NotificationEntity savedNotification = notificationService.saveNotification(notification);
        return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable int id) {
        notificationService.deleteNotificationById(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
