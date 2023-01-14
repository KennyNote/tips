package com.learning.notebook.tips.db.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "data-source.mysql")
public class MySQLDataSourceProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
