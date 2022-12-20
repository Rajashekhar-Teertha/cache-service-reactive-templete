package com.demo.redis.patterns.config;

import com.demo.redis.patterns.dto.ProductDto;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConfigurationProperties(prefix = "redison-cache")
@Data
//@ConditionalOnProperty(name = "enableCache", havingValue = "true")
public class RedisConfig {

    private String url;

    @Bean()
    RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress(url);
        return Redisson.create(config);
    }

    @Bean("reactiveRedisConnectionFactory")
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new RedissonConnectionFactory(redisson());
    }

    @Bean
    public ReactiveHashOperations<String, Integer, ProductDto> hashOperations(ReactiveRedisConnectionFactory redisConnectionFactory){
        var template = new ReactiveRedisTemplate<>(
                redisConnectionFactory,
                RedisSerializationContext.<String, ProductDto>newSerializationContext(new StringRedisSerializer())
                                         .hashKey(new GenericToStringSerializer<>(Integer.class))
                                         .hashValue(new Jackson2JsonRedisSerializer<>(ProductDto.class))
                                         .build()
        );
        return template.opsForHash();
    }

    @Bean
    public ReactiveRedisTemplate<String, ProductDto> reactiveRedisTemplateforProduct(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<ProductDto> serializer = new Jackson2JsonRedisSerializer<>(ProductDto.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, ProductDto> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, ProductDto> context = builder.value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplateforBooking(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, Object> context = builder.value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }


}
