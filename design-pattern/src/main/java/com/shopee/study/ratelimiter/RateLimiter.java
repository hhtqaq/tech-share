package com.shopee.study.ratelimiter;

import com.shopee.study.ratelimiter.alg.RateLimitAlg;
import com.shopee.study.ratelimiter.exception.BizException;
import com.shopee.study.ratelimiter.rule.ApiLimit;
import com.shopee.study.ratelimiter.rule.RateLimitRule;
import com.shopee.study.ratelimiter.rule.RuleConfig;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
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

    public RateLimiter()  {
        // 将限流规则配置文件ratelimiter-rule.yaml中的内容读取到RuleConfig中
        RuleConfig ruleConfig = null;
        try (InputStream in = this.getClass().getResourceAsStream("ratelimiter-rule.yaml")) {
            if (in != null) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(in, RuleConfig.class);
            }
        } catch (IOException e) {
            throw new RuntimeException("error loading rate limiter");
        }
        // 将限流规则构建成支持快速查找的数据结构RateLimitRule
        this.rule = new RateLimitRule(ruleConfig);
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
            RateLimitAlg newRateLimitCounter = new RateLimitAlg(apiLimit.getLimit());
            rateLimitCounter = counters.putIfAbsent(counterKey, newRateLimitCounter);
            if (rateLimitCounter == null) {
                rateLimitCounter = newRateLimitCounter;
            }
        }
        // 判断是否限流
        return rateLimitCounter.tryAcquire();
    }
}