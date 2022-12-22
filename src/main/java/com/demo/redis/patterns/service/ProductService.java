package com.demo.redis.patterns.service;

import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Object> getCache(String id);

    Mono<Void> putCache(String id, Object object);
}
