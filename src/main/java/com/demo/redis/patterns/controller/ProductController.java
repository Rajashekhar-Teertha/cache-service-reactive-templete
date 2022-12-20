package com.demo.redis.patterns.controller;

import com.demo.redis.patterns.dto.ProductDto;
import com.demo.redis.patterns.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    /*@GetMapping("{id}")
    public Mono<ProductDto> getProduct(@PathVariable Integer id){
        return this.productService.getProduct(id);
    }

    @PatchMapping("{id}")
    public Mono<Void> updateProduct(@PathVariable Integer id, @RequestBody Mono<ProductDto> mono){
        return this.productService.updateProduct(id, mono);
    }

    @PostMapping("/save")
    public Mono<Void> addBook(@RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @GetMapping("getBasedOnKey/{id}")
    public Mono<ProductDto> getBasedOnKey(@PathVariable String id) throws ExecutionException, InterruptedException {
        return this.productService.getProductBasedOnKey(id);
    }

    @PostMapping("/saveBasedOnKeyValue")
    public Mono<Void> saveBasedOnKeyValue(@RequestBody ProductDto productDto) {
        return productService.saveBasedOnKeyValue(productDto);
    }
*/

    /**
     * @param id
     * @return object
     */
    @GetMapping("/cacheGet/{id}")
    public Mono<Object> getCache(@PathVariable String id){
        log.info("processing get cache method ... {}", id);
        return productService.getCache(id);
    }

    /**
     * @param id
     * @return object
     */
    @PostMapping("/cachePut/{id}")
    public Mono<Void> putCache(@RequestBody Object object, @PathVariable("id") String id){
        log.info("processing put Cache method {}",object);
         return this.productService.putCache(id, object).then();
    }

}
