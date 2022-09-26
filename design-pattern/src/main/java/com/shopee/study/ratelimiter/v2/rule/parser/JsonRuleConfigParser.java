package com.shopee.study.ratelimiter.v2.rule.parser;

import com.shopee.study.ratelimiter.v2.rule.RuleConfig;

import java.io.InputStream;

/**
 * @description: JSON格式实现解析ruleconfigparse
 * @className: com.shopee.study.ratelimiter.v2.rule.parser.JsonRuleConfigParser
 * @copyRight: www.shopee.com by SZDC-BankingGroup
 * @author: haitao.huang
 * @createDate: 2022/9/26
 */
public class JsonRuleConfigParser implements    RuleConfigParser {

    /**
     * @param configText
     * @return
     */
    @Override
    public RuleConfig parse(final String configText) {
        return null;
    }

    /**
     * @param in
     * @return
     */
    @Override
    public RuleConfig parse(final InputStream in) {
        return null;
    }
}
