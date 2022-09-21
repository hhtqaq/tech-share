package com.shopee.study.ratelimiter.rule;

/**
 * @author haitao.huang
 */
public class RateLimitRule {
    public RateLimitRule(RuleConfig ruleConfig) {
        //...
    }

    public ApiLimit getLimit(String appId, String api) {
        //...
        return new ApiLimit();
    }
}