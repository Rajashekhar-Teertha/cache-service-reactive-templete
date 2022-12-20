//package com.demo.redis.patterns.service.impl;
//
//import com.demo.redis.patterns.dto.ProductDto;
//import com.demo.redis.patterns.entity.Product;
//import com.demo.redis.patterns.repository.ProductEntityRepository;
//import com.demo.redis.patterns.repository.ProductRepository;
//import com.demo.redis.patterns.service.ProductService;
//import com.demo.redis.patterns.util.EntityDtoUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//import reactor.util.function.Tuple2;
//
//import java.util.concurrent.ExecutionException;
//
//@Service
//@ConditionalOnProperty(name = "enableCache", havingValue = "false")
//public class ProductServiceWithNoCache implements ProductService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductEntityRepository productEntityRepository;
//
//    @Override
//    public Mono<ProductDto> getProduct(Integer id) {
//        return this.productEntityRepository.findById(id)
//                .map(EntityDtoUtil::toDtoProduct);
//    }
//
//    @Override
//    public Mono<Void> updateProduct(Integer id, Mono<ProductDto> mono) {
//        return this.productRepository.findById(id)
//                .zipWith(mono)
//                .doOnNext(t -> t.getT1().setDescription(t.getT2().getDescription()))
//                .map(Tuple2::getT1)
//                .flatMap(this.productRepository::save)
//                .then();
//    }
//
//    @Override
//    public Mono<Void> create(ProductDto productDto) {
//        Product product = EntityDtoUtil.toEntity(productDto);
//        return this.productRepository.save(product.setAsNew()).then();
//    }
//
//    @Override
//    public Mono<ProductDto> getProductBasedOnKey(String id) throws ExecutionException, InterruptedException {
//        return this.productEntityRepository.findById(Integer.parseInt(id))
//                .map(EntityDtoUtil::toDtoProduct);
//    }
//
//    @Override
//    public Mono<Void> saveBasedOnKeyValue(ProductDto productDto) {
//        Product product = EntityDtoUtil.toEntity(productDto);
//        return this.productRepository.save(product.setAsNew()).then();
//    }
//
//
//}
