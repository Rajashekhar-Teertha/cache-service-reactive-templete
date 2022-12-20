package com.demo.redis.patterns.service;

import com.demo.redis.patterns.dto.ProductDto;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    // hash Key based DS
//    Mono<ProductDto> getProduct(Integer id);
//
//    Mono<Void> updateProduct(Integer id, Mono<ProductDto> productDto);
//
//    Mono<Void> create(ProductDto mono);
//
//    //key value based DS
//    Mono<ProductDto> getProductBasedOnKey(String id) throws ExecutionException, InterruptedException;
//    //Mono<Void> createProductBasedOnKeyValue(ProductDto mono);
//    Mono<Void> saveBasedOnKeyValue(ProductDto productDto);

    Mono<Object> getCache(String id);

    Mono<Void> putCache(String id, Object object);
}
