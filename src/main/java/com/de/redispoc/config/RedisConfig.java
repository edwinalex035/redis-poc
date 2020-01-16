package com.de.redispoc.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@EnableCaching
@Configuration("cacheConfig")
@EnableConfigurationProperties(CacheConfigurationProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

  private static RedisCacheConfiguration createCacheConfiguration(Long ttl) {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(ttl));
  }

  @Bean
  public JedisConnectionFactory jedisConnectionFactory(CacheConfigurationProperties properties) {
    RedisStandaloneConfiguration redisStandaloneConfiguration =
        new RedisStandaloneConfiguration(properties.getRedisHost(), properties.getRedisPort());

    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory);

    return template;
  }

  @Bean
  public RedisCacheConfiguration cacheConfiguration(CacheConfigurationProperties properties) {
    return createCacheConfiguration(properties.getTimeoutSeconds());
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory jedisConnectionFactory, CacheConfigurationProperties properties) {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

    for(Entry<String, Long> cacheNameAndTimeout : properties.getCacheExpirations().entrySet()) {
      cacheConfigurations.put(cacheNameAndTimeout.getKey(), createCacheConfiguration(cacheNameAndTimeout.getValue()));
    }
    return RedisCacheManager
        .builder(jedisConnectionFactory)
        .cacheDefaults(cacheConfiguration(properties))
        .withInitialCacheConfigurations(cacheConfigurations).build();
  }
}
