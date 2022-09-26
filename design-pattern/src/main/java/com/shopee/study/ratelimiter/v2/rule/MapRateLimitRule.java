package com.shopee.study.ratelimiter.v2.rule;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 用map实现查找
 * @className: com.shopee.study.ratelimiter.v2.rule.MapRateLimitRule
 * @copyRight: www.shopee.com by SZDC-BankingGroup
 * @author: haitao.huang
 * @createDate: 2022/9/26
 */
public class MapRateLimitRule implements RateLimitRule {

    RuleConfig            ruleConfig;
    Map<String, ApiLimit> ruleConfigMap;

    public MapRateLimitRule(RuleConfig ruleConfig) {
        this.ruleConfig = ruleConfig;
        load();
    }

    public void load() {
        Map<String, ApiLimit> tempMap = new HashMap<>();
        for (final RuleConfig.AppRuleConfig config : ruleConfig.getConfigs()) {
            for (final ApiLimit limit : config.getLimits()) {
                tempMap.put(config.getAppId() + limit.getApi(), limit);
            }
        }
        ruleConfigMap = ImmutableMap.copyOf(tempMap);
    }

    /**
     * 通过appId和api获取api限流配置类
     *
     * @return ApiLimit
     */
    @Override
    public ApiLimit getLimit(final String appId, final String api) {
        return ruleConfigMap.get(appId + api);
    }
}
