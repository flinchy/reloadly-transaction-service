package com.chisom.transactionservice.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.auth-service")
public class ConfigUtils {
    private String registrationUrl;
    private String transactionUrl;
    private String accountUpdateUrl;
    private String loginUrl;
    private String validateUrl;
    private String basicAuth;
}
