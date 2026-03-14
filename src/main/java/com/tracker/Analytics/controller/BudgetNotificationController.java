package com.tracker.Analytics.controller;


import com.tracker.Analytics.dto.BudgetExceedEvent;
import com.tracker.Analytics.feign.BudgetInterface;
import com.tracker.Analytics.feign.ExpenseInterface;
import com.tracker.Analytics.producer.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class BudgetNotificationController {

    @Autowired
    ExpenseInterface expenseInterface;
    @Autowired
    BudgetInterface budgetInterface;

    @Autowired
    RabbitMQProducer producer;

    @GetMapping("/sendNotification")
    public ResponseEntity<String> sendNotification(@RequestHeader("X-User-Id") String userId, @RequestParam int month, @RequestParam(required = false) Integer year) {
        int targetYear = (year == null) ? LocalDate.now().getYear() : year;
        BigDecimal monthlyBudget = budgetInterface.getMonthlyBudget(userId, month, targetYear);
        BigDecimal totalExpense = expenseInterface.getTotalExpensesInAMonth(userId, month, targetYear);

        if ( totalExpense.compareTo(monthlyBudget) > 0) {
            BudgetExceedEvent event = new BudgetExceedEvent(
                    userId,
                    month,
                    targetYear,
                    monthlyBudget,
                    totalExpense,
                    LocalDateTime.now()
            );
            producer.sendNotification(event);
            return ResponseEntity.ok("sent");
        }
        return ResponseEntity.ok("Continue......");
    }
}
