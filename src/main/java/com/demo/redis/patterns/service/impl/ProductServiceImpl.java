package com.demo.redis.patterns.service.impl;

import com.demo.redis.patterns.config.TTLConfig;
import com.demo.redis.patterns.dto.ProductDto;
import com.demo.redis.patterns.exception.CacheProcessingException;
import com.demo.redis.patterns.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.ReactiveHashOperations;
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

//    @Autowired
//    private ReactiveHashOperations<String, Integer, ProductDto> hashOperations;


//    ReactiveRedisOperations<String, ProductDto> redisTemplate;
//
//    public ProductServiceImpl(ReactiveRedisOperations<String, ProductDto> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }


    // ReadThrough Pattern
   /* @Override
    public Mono<ProductDto> getProduct(Integer id) {
        return hashOperations.get(KEY, id)
                .switchIfEmpty(this.getFromDatabaseAndCache(id));
    }


    @Override
    public Mono<Void> updateProduct(Integer id, Mono<ProductDto> mono) {
        return super.updateProduct(id, mono)
                .then(this.hashOperations.remove(KEY, id))
                .then();
    }

    // Write Through Pattern
    @Override
    public Mono<Void> create(ProductDto productDto) {
        return super.create(productDto).then(this.hashOperations.put(KEY, productDto.getId(), productDto)).then();
    }

    @Override
    public Mono<ProductDto> getProductBasedOnKey(String key) throws ExecutionException, InterruptedException {
        return redisTemplate.opsForValue().get(key).switchIfEmpty(this.getFromDatabaseAndCacheKeyValue(key));

    }

    // Writethrough Key Value
    @Override
    public Mono<Void> saveBasedOnKeyValue(ProductDto productDto) {
        return super.saveBasedOnKeyValue(productDto).then(redisTemplate.opsForValue()
                .set(String.valueOf(productDto.getId()), productDto,
                        Duration.ofMinutes(Long.parseLong(config.getCacheTTL())))).then();
    }

    private Mono<ProductDto> getFromDatabaseAndCache(Integer id) {
        return super.getProduct(id)
                .flatMap(dto -> this.hashOperations.put(KEY, id, dto)
                        .thenReturn(dto));
    }


    private Mono<ProductDto> getFromDatabaseAndCacheKeyValue(String key) throws ExecutionException, InterruptedException {
  *//*      ProductDto productDto = super.getProductBasedOnKey(key).subscribe(productDto1 -> {
            ProductDto.builder().id(productDto1.getId())
                    .price(productDto1.getPrice())
                    .description(productDto1.getDescription())
        });*//*

        ProductDto productDto = super.getProductBasedOnKey(key).toFuture().get();
        return redisTemplate.opsForValue()
                .set(String.valueOf(key), productDto,
                        Duration.ofMinutes(Long.parseLong(config.getCacheTTL()))).thenReturn(productDto);
    }*/

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