package com.demo.redis.patterns.service.impl;

import com.demo.redis.patterns.exception.CacheProcessingException;
import com.demo.redis.patterns.service.CacheAsideService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CacheAsideAsideServiceImpl<K,V> implements CacheAsideService<K,V> {

    private static final String KEY = "product";

    /*@Autowired
    TTLConfig config;*/

    @Autowired
    ReactiveRedisTemplate<Object,Object> reactiveRedisTemplate;
    @Override
    public Mono<V> getCache(K key) {
            log.info("reading from cache with value key {} ",key);
            return (Mono<V>) reactiveRedisTemplate.opsForValue().get(key).doOnError(e -> {
                log.error("exception occurred while fetching data from cache {}", e.getMessage());
                throw new CacheProcessingException(e.getMessage());
            });
    }

    @Override
    public Mono<Void> putCache(K id, V object) {
            log.info("writing to cache with value key {} , object {}",id,object);
            return reactiveRedisTemplate.opsForValue().set(id, object).then().doOnError(e -> {
                log.error("exception occurred while processing the cache {}", e.getMessage());
                throw new CacheProcessingException(e.getMessage());
            });
//            return reactiveRedisTemplateforBooking.opsForValue().set(id, object,
//                    Duration.ofSeconds(Long.parseLong(config.getCacheTTL()))).then();
    }

}