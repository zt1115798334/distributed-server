package com.example.distributedcommoncache.config;


import com.example.distributedcommoncache.service.RedisService;
import com.example.distributedcommoncache.service.template.JSONArrayRedisTemplate;
import com.example.distributedcommoncache.service.template.JSONObjectRedisTemplate;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2019/8/1 11:49
 * description:
 */
@Configuration
public class RedisConfigMain extends CachingConfigurerSupport {

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    public GenericObjectPoolConfig redisPool() {
        return new GenericObjectPoolConfig();
    }

    /**
     * 配置第一个数据源的
     *
     * @return 数据源
     */
    @Bean(name = "redisConfigZero")
    @ConfigurationProperties(prefix = "spring.redis.redis-zero")
    public RedisStandaloneConfiguration redisConfigZero() {
        return new RedisStandaloneConfiguration();
    }

    /**
     * 配置第二个数据源
     *
     * @return 数据源
     */
    @Bean(name = "redisConfigFirst")
    @ConfigurationProperties(prefix = "spring.redis.redis-first")
    public RedisStandaloneConfiguration redisConfigFirst() {
        return new RedisStandaloneConfiguration();
    }

    /**
     * 配置第一个数据源的连接工厂
     * 这里注意：需要添加@Primary 指定bean的名称，目的是为了创建两个不同名称的LettuceConnectionFactory
     */
    @Primary
    @Bean("factoryZero")
    public LettuceConnectionFactory factoryZero(GenericObjectPoolConfig config, @Qualifier("redisConfigZero") RedisStandaloneConfiguration redisConfigZero) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfigZero, clientConfiguration);
    }

    @Bean("factoryFirst")
    public LettuceConnectionFactory factoryFirst(GenericObjectPoolConfig config, @Qualifier("redisConfigFirst") RedisStandaloneConfiguration redisConfigFirst) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfigFirst, clientConfiguration);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(@Qualifier("factoryZero") RedisConnectionFactory factoryZero) {
        return new StringRedisTemplate(factoryZero);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplateFirst(@Qualifier("factoryFirst") RedisConnectionFactory factoryFirst) {
        return new StringRedisTemplate(factoryFirst);
    }

    @Bean
    public JSONObjectRedisTemplate jsonObjectRedisTemplate(@Qualifier("factoryZero") RedisConnectionFactory factoryZero, ObjectMapper objectMapper) {
        JSONObjectRedisTemplate template = new JSONObjectRedisTemplate(factoryZero);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getObjectJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public JSONObjectRedisTemplate jsonObjectRedisTemplateFirst(@Qualifier("factoryFirst") RedisConnectionFactory factoryFirst,
                                                                ObjectMapper objectMapper) {
        JSONObjectRedisTemplate template = new JSONObjectRedisTemplate(factoryFirst);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getObjectJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public JSONArrayRedisTemplate jsonArrayRedisTemplate(@Qualifier("factoryZero") RedisConnectionFactory factoryZero,
                                                         ObjectMapper objectMapper) {
        JSONArrayRedisTemplate template = new JSONArrayRedisTemplate(factoryZero);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getObjectJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public JSONArrayRedisTemplate jsonArrayRedisTemplateFirst(@Qualifier("factoryFirst") RedisConnectionFactory factoryFirst,
                                                              ObjectMapper objectMapper) {
        JSONArrayRedisTemplate template = new JSONArrayRedisTemplate(factoryFirst);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getObjectJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("factoryZero") RedisConnectionFactory factoryZero,
                                                       ObjectMapper objectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getObjectJackson2JsonRedisSerializer(objectMapper);
        template.setConnectionFactory(factoryZero);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public DefaultRedisScript<Boolean> identityRedisScript() {
        DefaultRedisScript<Boolean> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Boolean.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/IdentityRedisScript.lua")));
        String sha1 = defaultRedisScript.getSha1();
        System.out.println("sha1 = " + sha1);
        return defaultRedisScript;
    }

    @Bean
    public KeyGenerator KeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Bean
    public CacheManager cacheManager(@Qualifier("factoryZero") RedisConnectionFactory factoryZero,
                                     ObjectMapper objectMapper) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(factoryZero),
                this.getRedisCacheConfigurationWithTtl(600, objectMapper), // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationMap(objectMapper) // 指定 key 策略
        );
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap(ObjectMapper objectMapper) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("findUserByEffective", this.getRedisCacheConfigurationWithTtl(18000, objectMapper));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds, ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getObjectJackson2JsonRedisSerializer(objectMapper);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }

    private Jackson2JsonRedisSerializer<Object> getObjectJackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        ObjectMapper copy = objectMapper.copy();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        copy.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        copy.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(copy);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    /**
     * 初始化监听器
     *
     * @param factoryZero     connectionFactory
     * @param listenerAdapter listenerAdapter
     * @return RedisMessageListenerContainer
     */
    @Bean
    public RedisMessageListenerContainer container(@Qualifier("factoryZero") RedisConnectionFactory factoryZero,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factoryZero);
        //配置监听通道
        container.addMessageListener(listenerAdapter, new PatternTopic("test"));// 通道的名字
        return container;
    }

    /**
     * 利用反射来创建监听到消息之后的执行方法
     *
     * @param redisService redisService
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisService redisService) {
        return new MessageListenerAdapter(redisService, "receiveMessage");
    }

}
