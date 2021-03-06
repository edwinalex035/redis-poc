package com.de.redispoc.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cache")
@Data
public class CacheConfigurationProperties {
  private long timeoutSeconds = 60;

  private Map<String, Long> cacheExpirations = new HashMap<>();

}
