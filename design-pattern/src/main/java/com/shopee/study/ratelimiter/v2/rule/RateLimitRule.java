package com.shopee.study.ratelimiter.v2.rule;

import java.util.List;

/**
 * @author haitao.huang
 */
public interface RateLimitRule {


    /**
     * 通过appId和api获取api限流配置类
     * @return  ApiLimit
     */
     ApiLimit getLimit(String appId, String api);
}