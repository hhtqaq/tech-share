package com.shopee.study.ratelimiter.v1.rule;

import java.util.List;

/**
 * @author haitao.huang
 */
public class RateLimitRule {
    public RuleConfig ruleConfig;

    public RateLimitRule(RuleConfig ruleConfig) {
        //
        this.ruleConfig = ruleConfig;
    }

    /**
     * 通过appId和api获取api限流配置类
     * @return  ApiLimit
     */
    public ApiLimit getLimit(String appId, String api) {
        //...
        final List<RuleConfig.AppRuleConfig> configs = ruleConfig.getConfigs();
        for (final RuleConfig.AppRuleConfig config : configs) {
            if (config.getAppId().equals(appId)) {
                final List<ApiLimit> limits = config.getLimits();
                for (final ApiLimit limit : limits) {
                    if (limit.getApi().equals(api)) {
                        return limit;
                    }
                }
            }
        }
        return null;
    }
}