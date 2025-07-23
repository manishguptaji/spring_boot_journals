package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T getValue(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            try {
                return type.cast(value);
            } catch (ClassCastException e) {
                log.error("Error casting value for key {}: {}", key, e.getMessage());
            }
        }
        return null;
    }

    public void setValue(String key, Object value, Long expirationTime) {
        redisTemplate.opsForValue().set(key, value, expirationTime, TimeUnit.MINUTES);
        log.info("Set value for key {}: {}", key, value);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
        log.info("Deleted value for key: {}", key);
    }

    public boolean exists(String key) {
        boolean exists = redisTemplate.hasKey(key);
        log.info("Key {} exists: {}", key, exists);
        return exists;
    }
}