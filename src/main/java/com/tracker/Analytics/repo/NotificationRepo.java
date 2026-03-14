package com.tracker.Analytics.repo;

import com.tracker.Analytics.model.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationLog, Long> {

    boolean existsByUserIdAndEventTypeAndCreatedAtAfter(String s, String budgetExceed, LocalDateTime oneDayAgo);
}
