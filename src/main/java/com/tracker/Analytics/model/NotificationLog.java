package com.tracker.Analytics.model;

import com.tracker.Analytics.enums.NotificationEventType;
import com.tracker.Analytics.enums.NotificationStatus;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity

@Table(name = "notification_logs")
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    @Enumerated(EnumType.STRING)
    private NotificationEventType eventType;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    @Column(columnDefinition = "TEXT")
    private String messagePayload;
    private LocalDateTime createdAt;

    public NotificationLog(String userId, NotificationEventType eventType, NotificationStatus status) {
        this.userId = userId;
        this.eventType = eventType;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

}


