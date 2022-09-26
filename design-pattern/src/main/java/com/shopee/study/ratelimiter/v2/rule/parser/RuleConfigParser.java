package com.shopee.study.ratelimiter.v2.rule.parser;

import com.shopee.study.ratelimiter.v2.rule.RuleConfig;

import java.io.InputStream;

/**
 * @description: 解析配置抽象接口
 * @className: com.shopee.study.ratelimiter.v2.rule.parser.RuleConfigParser
 * @copyRight: www.shopee.com by SZDC-BankingGroup
 * @author: haitao.huang
 * @createDate: 2022/9/26
 */
public interface RuleConfigParser {
    RuleConfig parse(String configText);
    RuleConfig parse(InputStream in);
}
