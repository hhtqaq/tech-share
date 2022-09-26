package com.shopee.study.ratelimiter.v2.rule.datasource;

import com.shopee.study.ratelimiter.v2.rule.RuleConfig;

public interface RuleConfigSource {
    RuleConfig load();
}
