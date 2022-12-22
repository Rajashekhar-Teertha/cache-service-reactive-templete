package com.demo.redis.patterns.service.impl;

import com.demo.redis.patterns.config.TTLConfig;
import com.demo.redis.patterns.exception.CacheProcessingException;
import com.demo.redis.patterns.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private static final String KEY = "product";

    @Autowired
    TTLConfig config;

    @Autowired
    ReactiveRedisOperations<String,Object> reactiveRedisTemplateforBooking;


    /**
     * @param id
     * @return object
     */
    @Override
    public Mono<Object> getCache(String id) {

        try{
            log.info("reading from cache with value key {} ",id);
            return reactiveRedisTemplateforBooking.opsForValue().get(id);
        }
        catch(Exception e){
            log.error("exception occurred while fetching data from cache {}", e.getMessage());
            throw new CacheProcessingException(e.getMessage());
        }


    }

    /**
     * @param id
     * @param object
     * @return
     */
    @Override
    public Mono<Void> putCache(String id, Object object) {

        try{
            log.info("writing to cache with value key {} , object {}",id,object);
            return reactiveRedisTemplateforBooking.opsForValue().set(id, object).then();
//            return reactiveRedisTemplateforBooking.opsForValue().set(id, object,
//                    Duration.ofSeconds(Long.parseLong(config.getCacheTTL()))).then();
        }
        catch(Exception e){
            log.error("exception occurred while processing the cache {}", e.getMessage());
            throw new CacheProcessingException(e.getMessage());
        }



    }
}