package com.demo.redis.patterns.controller;

import com.demo.redis.patterns.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
@Slf4j
public class ProductController<K,V> {


    @Autowired
    private ProductService productService;

    /**
     * @param id
     * @return object
     */
    @GetMapping("/cacheGet/{id}")
    public Mono<V> getCache(@PathVariable K id){
        log.info("processing get cache method ... {}", id);
        return productService.getCache(id);
    }

    /**
     * @param id
     * @return object
     */
    @PostMapping("/cachePut/{id}")
    public Mono<Void> putCache(@RequestBody V object, @PathVariable("id") K id){
        log.info("processing put Cache method {}",object);
         return this.productService.putCache(id, object).then();
    }

}
