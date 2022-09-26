package com.shopee.study.ratelimiter.v2.rule.parser;

import com.shopee.study.ratelimiter.v2.rule.RuleConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * @description: yaml 格式实现解析配置实现类
 * @className: com.shopee.study.ratelimiter.v2.rule.parser.YamlRuleConfigParser
 * @copyRight: www.shopee.com by SZDC-BankingGroup
 * @author: haitao.huang
 * @createDate: 2022/9/26
 */
public class YamlRuleConfigParser implements RuleConfigParser {
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
        if (in != null) {
            Yaml yaml = new Yaml();
            return yaml.loadAs(in,RuleConfig.class);
        }
        return null;
    }
}
