package com.example.resilience.autoconfigure;

import com.example.resilience.config.ResilienceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Auto-configuration entry point. When this JAR is on the classpath,
 * ResilienceConfig will be imported automatically.
 */
@Configuration
@Import({ ResilienceConfig.class })
public class ResilienceAutoConfiguration {
}
