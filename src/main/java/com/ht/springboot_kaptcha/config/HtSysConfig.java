package com.ht.springboot_kaptcha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目相关配置
 */
@Component
@ConfigurationProperties(prefix = "ht")
@Data
public class HtSysConfig {

    /**
     * 项目名
     */
    private String name;

    /**
     * 版本号
     */
    private String version;

    /**
     * 验证码类型
     */
    private static String captchaType;

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        HtSysConfig.captchaType = captchaType;
    }
}
