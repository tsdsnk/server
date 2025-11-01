package com.example.mydemo.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("strategy")
public class StrategyConfig {
    private Integer maxApplicationStrategy;
}
