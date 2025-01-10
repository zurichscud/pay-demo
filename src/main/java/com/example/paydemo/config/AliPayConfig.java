package com.example.paydemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AliPayConfig {

    // 应用ID
    private String appId;

    // 私钥
    private String appPrivateKey;

    // 公钥
    private String alipayPublicKey;

    // 回调地址
    private String notifyUrl;

    // 支付宝网关
    private String gatewayUrl;


}
