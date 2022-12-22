//package com.demo.redis.patterns.util;
//
//import com.demo.redis.patterns.dto.ProductDto;
//import com.demo.redis.patterns.entity.Product;
//import com.demo.redis.patterns.entity.ProductEntity;
//
//public class EntityDtoUtil {
//
//    public static ProductDto toDto(Product product) {
//        ProductDto dto = new ProductDto();
//        dto.setId(product.getId());
//        dto.setPrice(product.getPrice());
//        dto.setDescription(product.getDescription());
//        return dto;
//    }
//
//    public static ProductDto toDtoProduct(ProductEntity product) {
//        ProductDto dto = new ProductDto();
//        dto.setId(product.getId());
//        dto.setPrice(product.getPrice());
//        dto.setDescription(product.getDescription());
//        return dto;
//    }
//
//    public static Product toEntity(ProductDto dto) {
//       /* Product product = new Product();
//        product.setId(dto.getId());
//        product.setPrice(dto.getPrice());
//        product.setDescription(dto.getDescription());
//        return product;*/
//        return Product.builder().id(dto.getId()).price(dto.getPrice()).description(dto.getDescription()).build();
//    }
//
//}
