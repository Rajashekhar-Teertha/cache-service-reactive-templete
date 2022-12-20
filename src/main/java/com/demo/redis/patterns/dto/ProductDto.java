package com.demo.redis.patterns.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDto {

    private Integer id;
    private String description;
    private Double price;


}
