package com.tracker.Analytics.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("EXPENSE-SERVICE")
public interface ExpenseInterface {

    @GetMapping("/allexpenses")
    BigDecimal getTotalExpensesInAMonth(@RequestHeader("X-User-Id") String userId, @RequestParam int month, @RequestParam Integer year);
}
