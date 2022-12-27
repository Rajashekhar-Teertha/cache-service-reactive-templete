package com.demo.redis.patterns.service;

import reactor.core.publisher.Mono;


public interface ProductService<K,V> {
    Mono<V> getCache(K key);

    Mono<Void> putCache(K id, V object);
}
