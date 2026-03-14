package com.tracker.Analytics.feign;

import com.tracker.Analytics.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "USER-SERVICE", configuration = FeignClientConfig.class)
public interface BudgetInterface {

    @GetMapping("/getMonthlyBudget")
    BigDecimal getMonthlyBudget(@RequestHeader("X-User-Id") String userId, @RequestParam int month, @RequestParam(required=false) Integer year);
}
