package cn.hnist.sharo.mcinema.db.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//固定的模板
@Configuration
public class RedisConfig {
    /**
     * 一般的reids存储用这个template,存为json
     *
     * @param factory
     * @return
     */
    @Bean(name = "redisTemplate")
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);


        //json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.registerModule(new JavaTimeModule()); // 添加JavaTimeModule模块
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 将日期序列化为ISO-8601格式
        jackson2JsonRedisSerializer.setObjectMapper(om);


        // 配置各个类型的序列化方式
        //String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();


        return template;
    }

    /**
     * 由于session存储redis存在序列化问题,只能使用自定义的序列化存储为byte[]
     *
     * @param factory
     * @return
     */
    @Bean(name = "redisSessionTemplate")
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisSessionTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);

        RedisSessionSerializer redisSessionSerializer = new RedisSessionSerializer();

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(redisSessionSerializer);
        template.setHashValueSerializer(redisSessionSerializer);
        template.setDefaultSerializer(redisSessionSerializer);
        template.afterPropertiesSet();

        return template;
    }

}
