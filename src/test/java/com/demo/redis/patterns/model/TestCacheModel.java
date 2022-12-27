package com.demo.redis.patterns.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestCacheModel {
    private String field1;
    private String field2;
}
