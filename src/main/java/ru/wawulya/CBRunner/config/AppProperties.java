package ru.wawulya.CBRunner.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "ticket")
@Validated
public class AppProperties {

    private String baseUrl;
    private Map<String,String> api;


}
