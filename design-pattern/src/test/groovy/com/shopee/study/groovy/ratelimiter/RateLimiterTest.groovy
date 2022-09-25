package com.shopee.study.groovy.ratelimiter

import com.shopee.study.ratelimiter.RateLimiter
import spock.lang.Specification

import java.util.concurrent.TimeUnit

class RateLimiterTest extends Specification {

    //测试100 ms 超过10此请求 触发限流
    def "Limit : #condition"() {
        given: "定义参数"
        //test limit method         limit: 10 unit:100
        def rateLimiter = new RateLimiter();


        when: "检验"
        def limit1 = rateLimiter.limit(appId, url);
        def limit2 = rateLimiter.limit(appId, url);
        def limit3 = rateLimiter.limit(appId, url);
        def limit4 = rateLimiter.limit(appId, url);
        TimeUnit.MILLISECONDS.sleep(100)
        def limit5 = rateLimiter.limit(appId, url);
        def limit6 = rateLimiter.limit(appId, url);
        def limit7 = rateLimiter.limit(appId, url);
        then: "验证"
        !limit1
        !limit2
        limit3
        limit4
        !limit5
        !limit6
        limit7


        where: "测试数据"
        condition | appId   | url
        "正例子"  | "app-1" | "/v1/user"
    }


}
