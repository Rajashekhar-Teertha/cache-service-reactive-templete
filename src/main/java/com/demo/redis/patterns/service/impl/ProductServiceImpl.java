package com.demo.redis.patterns.service.impl;

import com.demo.redis.patterns.config.TTLConfig;
import com.demo.redis.patterns.exception.CacheProcessingException;
import com.demo.redis.patterns.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class ProductServiceImpl<K,V> implements ProductService<K,V> {

    private static final String KEY = "product";

    /*@Autowired
    TTLConfig config;*/

    @Autowired
    ReactiveRedisTemplate<Object,Object> reactiveRedisTemplate;
    @Override
    public Mono<V> getCache(K key) {
        try{
            log.info("reading from cache with value key {} ",key);
            return (Mono<V>) reactiveRedisTemplate.opsForValue().get(key);
        }
        catch(Exception e){
            log.error("exception occurred while fetching data from cache {}", e.getMessage());
            throw new CacheProcessingException(e.getMessage());
        }
    }

    @Override
    public Mono<Void> putCache(K id, V object) {
        try{
            log.info("writing to cache with value key {} , object {}",id,object);
            return reactiveRedisTemplate.opsForValue().set(id, object).then();
//            return reactiveRedisTemplateforBooking.opsForValue().set(id, object,
//                    Duration.ofSeconds(Long.parseLong(config.getCacheTTL()))).then();
        }
        catch(Exception e){
            log.error("exception occurred while processing the cache {}", e.getMessage());
            throw new CacheProcessingException(e.getMessage());
        }
    }

}