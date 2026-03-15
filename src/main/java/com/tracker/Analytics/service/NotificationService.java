package com.tracker.Analytics.service;

import com.tracker.Analytics.dto.BudgetExceedEvent;
import com.tracker.Analytics.enums.NotificationEventType;
import com.tracker.Analytics.enums.NotificationStatus;
import com.tracker.Analytics.model.NotificationLog;
import com.tracker.Analytics.repo.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    public NotificationRepo notificationRepo;

    public void processBudgetAlert(BudgetExceedEvent event) {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        boolean alreadySent = notificationRepo.existsByUserIdAndEventTypeAndCreatedAtAfter(event.userId() , NotificationEventType.BUDGET_EXCEEDED, oneDayAgo);

        if(alreadySent) {
            System.out.println("Already email has been sent to the user " + event.userId());
            return;
        }

        try {
            sendActualEmail(event);
            notificationRepo.save(new NotificationLog(
                    event.userId(),
                    NotificationEventType.BUDGET_EXCEEDED,
                    NotificationStatus.SENT
            ));
        } catch (Exception e) {
            System.out.println("Error!!");
            throw e;
        }
    }

    private void sendActualEmail(BudgetExceedEvent event) {
        System.out.println("Email is sent for userId: " + event.userId());
    }
}
