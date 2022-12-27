package com.demo.redis.patterns.service;

import com.demo.redis.patterns.model.TestCacheModel;
import com.demo.redis.patterns.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
public class ProductServiceImplTest {

    @Mock
    ReactiveRedisOperations<String,Object> reactiveRedisTemplateforBooking;

    @MockBean
    ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;

    @MockBean
    RedissonClient redisson;

    @Mock
    private ReactiveValueOperations valueOperations;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void getCacheTest(){
        TestCacheModel post = TestCacheModel.builder().field1("test1").field2("delhi").build();
       when(reactiveRedisTemplateforBooking.opsForValue()).thenReturn(valueOperations);
       when(valueOperations.get(anyString())).thenReturn(Mono.just(post));
        Mono<Object> response =  productService.getCache("test1");
        Assertions.assertNotNull(response);
    }

    @Test
    public void putCacheTest(){
        TestCacheModel post = TestCacheModel.builder().field1("test1").field2("delhi").build();
        when(reactiveRedisTemplateforBooking.opsForValue()).thenReturn(valueOperations);
        Mono<Void> void1 = Mockito.mock(Mono.class);
        when(valueOperations.set(anyString(),any())).thenReturn(void1);
        Mono<Void> response =  productService.putCache("test1",post);
        Assertions.assertNotNull(response);
    }
}
