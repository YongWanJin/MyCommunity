package com.example.MyCommunity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
/** 레디스 서버 관련 (로그아웃용, 나중에 완성할 예정) */
public class CacheConfig {

  // # 레디스 서버가 있는 host ip, port번호 적용
  @Value("${spring.redis.host}")
  private String host;
  @Value("${spring.redis.port}")
  private int port;

  /** 레디스 관련 bean 초기화 */
  @Bean
  public RedisConnectionFactory redisConnectionFactory(){
    // # single instance(단일 노드)로 레디스 서버를 구축
    // (그 외에도 Sentinel(Master-Slave), Cluster(다중 노드) 방식이 있다.)
    RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
    conf.setHostName(this.host); // ip 주소 적용
    conf.setPort(this.port); // 포트 번호 적용
    return new LettuceConnectionFactory(conf);
  }

  /** 레디스를 캐시 서버로서 사용하기 위한 bean 생성 및 초기화 */
  @Bean
  public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){

    RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
        // # 직렬화(Serialization)
        // 자바 객체를 바이트 형태로 변환하여 다른 프로그램에서도 쓸 수 있게하는 작업
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            // key 직렬화
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
            // value 직렬화
    ;

    return RedisCacheManager.RedisCacheManagerBuilder.
        fromConnectionFactory(redisConnectionFactory)
        .cacheDefaults(conf)
        .build();
  }
}
