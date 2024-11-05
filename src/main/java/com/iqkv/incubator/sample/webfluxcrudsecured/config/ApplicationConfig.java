package com.iqkv.incubator.sample.webfluxcrudsecured.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({ApplicationBuildInfoProperties.class, JwtTokenProperties.class})
class ApplicationConfig {

}
