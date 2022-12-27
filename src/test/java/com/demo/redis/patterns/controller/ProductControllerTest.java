package com.demo.redis.patterns.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.demo.redis.patterns.config.RedisConfig;
//import com.demo.redis.patterns.dto.ProductDto;
import com.demo.redis.patterns.model.TestCacheModel;
import com.demo.redis.patterns.service.ProductService;
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
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Mono;

/**
 * Unit Testing - Approach 1 - Using Mockito
 *
 * @author learninjava.com
 *
 */
@SpringBootTest()
@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;
    @Mock
    private ClientResponse clientResponse;

    @Mock
    private Mono<TestCacheModel> postResponseMock;

    @Mock
    private ProductService productService;

    @MockBean
    RedissonClient redisson;

    @MockBean
    ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;


    @MockBean
    ReactiveRedisTemplate<Object,Object> reactiveRedisTemplate;

    @InjectMocks
    private ProductController controllerMock;

    @SuppressWarnings("unchecked")
    @Test
    public void test_createPost() throws Exception {
        TestCacheModel post = TestCacheModel.builder().field1("test1").field2("delhi").build();
        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(anyString())).thenReturn(requestBodySpecMock);
        when(requestBodySpecMock.header(any(), any())).thenReturn(requestBodySpecMock);
        when(requestBodySpecMock.body(any())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.exchange()).thenReturn(Mono.just(clientResponse));
        Mono<Void> void1 = Mockito.mock(Mono.class);
        when(productService.putCache(any(),any())).thenReturn(void1);
        Mono<Void> response = controllerMock.putCache(post,"test1");
        Assertions.assertNotNull(response);
        verify(productService,Mockito.times(1)).putCache(any(),any());
    }


    @SuppressWarnings("unchecked")
    @Test
    public void test_getPost() throws Exception {

        TestCacheModel post = TestCacheModel.builder().field1("test1").field2("delhi").build();

        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(TestCacheModel.class)).thenReturn(Mono.just(post));
        when(productService.getCache(any())).thenReturn(Mono.just(post));
        Mono<Object> response = controllerMock.getCache("test1");

        Assertions.assertNotNull(response);
        verify(productService,Mockito.times(1)).getCache(any());
    }


}
