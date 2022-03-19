package com.liaomessi.design_patterns.strategy_patterns;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class UnifiedPlanAggregateRootTest {

    @Test
    void calculatePlan() {
        UnifiedPlanAggregateRoot planAggregateRoot = new UnifiedPlanAggregateRoot(LocalDate.of(2022, 03, 15));
        List<LocalDate> googlePlans = planAggregateRoot.calculatePlan("Google");
        Assert.assertEquals(7, googlePlans.size());
        googlePlans.forEach(plan -> Assert.assertTrue(plan.isBefore(LocalDate.of(2022, 03, 23))));
    }

    @Test
    void calculatePlanWithError() {
        // 抛异常
        UnifiedPlanAggregateRoot planAggregateRoot = new UnifiedPlanAggregateRoot(LocalDate.of(2022, 03, 15));
        UnifiedPlanAggregateRoot planAggregateRoot2 = new UnifiedPlanAggregateRoot(LocalDate.of(2022, 04, 15));
        List<LocalDate> googlePlans = planAggregateRoot.calculatePlan("Google");
        Assert.assertEquals(7, googlePlans.size());
        googlePlans.forEach(plan -> Assert.assertTrue(plan.isBefore(LocalDate.of(2022, 03, 23))));
    }
}