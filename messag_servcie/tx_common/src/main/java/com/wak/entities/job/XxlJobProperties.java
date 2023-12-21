package com.wak.entities.job;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuankang
 * @date 2023/12/11 16:31
 * @Description TODO xxl-job配置对象
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "xxl.job")
@Component
@Data
public class XxlJobProperties {
    private String adminAddresses;
    private String accessToken;
    private String appName;
    private String address;
    private String ip;
    private int port;
    private String logPath;
    private int logRetentionDays;
}
