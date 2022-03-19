package com.liaomessi.design_patterns.strategy_patterns;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Blue
 * @date 2022/3/19
 * @description
 */
@AllArgsConstructor
public class UnifiedPlanAggregateRoot {

    // 计算计划的基准时间
    private LocalDate basedDate;

    // 各个合作方的策略map
    private static Map<String, PartnerPlanPolicy> partnerPoliciesMap = new HashMap<>();

    // 初始化各个合作方的策略
    {
        partnerPoliciesMap.put("Google", new GooglePlanPolicy());
    }

    // 根据给定的合作方，调用对应的策略进行计算计划
    public List<LocalDate> calculatePlan(String partner) {
        return partnerPoliciesMap.get(partner).calculatePlan();
    }

    /**
     * 这里我定义一个内部的policy接口，然后实现了一个google的policy，以后可能会有bing，facebook等等的policy
     */
    interface PartnerPlanPolicy {
        List<LocalDate> calculatePlan();
    }

    /**
     * google的一个计算计划策略实现,我们简单返回一个后面一个星期的日期用来测试
     */
    class GooglePlanPolicy implements PartnerPlanPolicy {

        private static final int GOOGLE_PLAN_LIMITATION = 7;

        @Override
        public List<LocalDate> calculatePlan() {
            List<LocalDate> plans = new ArrayList<>();
            for (int i = 0; i < GOOGLE_PLAN_LIMITATION; i++) {
                plans.add(basedDate.plusDays(1));
            }
            return plans;
        }
    }
}
