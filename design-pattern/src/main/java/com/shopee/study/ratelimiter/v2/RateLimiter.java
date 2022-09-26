package com.shopee.study.ratelimiter.v2;

import com.shopee.study.ratelimiter.v2.algorithm.RateLimitAlg;
import com.shopee.study.ratelimiter.v2.rule.ApiLimit;
import com.shopee.study.ratelimiter.v2.rule.MapRateLimitRule;
import com.shopee.study.ratelimiter.v2.rule.RateLimitRule;
import com.shopee.study.ratelimiter.v2.rule.RuleConfig;
import com.shopee.study.ratelimiter.v2.rule.datasource.FileRuleConfigSource;
import com.shopee.study.ratelimiter.v2.rule.datasource.RuleConfigSource;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import exception.BizException;
import org.yaml.snakeyaml.Yaml;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author haitao.huang
 */
public class RateLimiter {
    private static final Logger                                  log      = LoggerFactory.getLogger(RateLimiter.class);
    /**
     * 为每个api在内存中存储限流计数器
     */
    private final        ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();
    private final        RateLimitRule                           rule;

    public RateLimiter() {
        //改动主要在这里：调用RuleConfigSource类来实现配置加载
        RuleConfigSource configSource = new FileRuleConfigSource();
        RuleConfig ruleConfig = configSource.load();
        this.rule = new MapRateLimitRule(ruleConfig);
    }

    public boolean limit(String appId, String url) throws BizException {

        ApiLimit apiLimit = rule.getLimit(appId, url);
        if (apiLimit == null) {
            return true;
        }

        // 获取api对应在内存中的限流计数器（rateLimitCounter）
        String counterKey = appId + ":" + apiLimit.getApi();
        RateLimitAlg rateLimitCounter = counters.get(counterKey);
        if (rateLimitCounter == null) {
            RateLimitAlg newRateLimitCounter = new RateLimitAlg(apiLimit.getLimit(), apiLimit.getUnit());
            rateLimitCounter = counters.putIfAbsent(counterKey, newRateLimitCounter);
            if (rateLimitCounter == null) {
                rateLimitCounter = newRateLimitCounter;
            }
        }
        // 判断是否限流
        return !rateLimitCounter.tryAcquire();
    }
}