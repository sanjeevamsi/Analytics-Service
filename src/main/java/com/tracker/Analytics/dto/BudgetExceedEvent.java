package com.tracker.Analytics.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BudgetExceedEvent (
    String userId,
    int month,
    int year,
    BigDecimal monthlyBudget,
    BigDecimal totalExpense,
    LocalDateTime timestamp
) {}

