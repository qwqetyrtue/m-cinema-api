package cn.hnist.sharo.mcinema.db.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 专门用于Session存储redis
 */
@Service
public class RedisSessionUtil {

    private final Log logger = LogFactory.getLog(RedisSessionUtil.class);

    private final RedisTemplate<String, Object> redisSessionTemplate;

    public RedisSessionUtil(RedisTemplate<String, Object> redisSessionTemplate) {
        this.redisSessionTemplate = redisSessionTemplate;
    }

    //=============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                try {
                    redisSessionTemplate.expire(key, time, TimeUnit.SECONDS);
                } finally {
                    RedisConnectionUtils.unbindConnection(redisSessionTemplate.getConnectionFactory());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 匹配所有符合模式的key
     *
     * @param pattern 模式
     * @return key set
     */
    public Set<String> getKeys(String pattern) {
        return redisSessionTemplate.keys(pattern);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        try {
            return redisSessionTemplate.getExpire(key, TimeUnit.SECONDS);
        } finally {
            RedisConnectionUtils.unbindConnection(redisSessionTemplate.getConnectionFactory());
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisSessionTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            try {
                if (key.length == 1) {
                    redisSessionTemplate.delete(key[0]);
                } else {
                    redisSessionTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
                }
            } finally {
                RedisConnectionUtils.unbindConnection(redisSessionTemplate.getConnectionFactory());
            }
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        Object object;
        try {
            if (key == null) {
                object = null;
            } else {
                object = redisSessionTemplate.opsForValue().get(key);
            }
        } finally {
            RedisConnectionUtils.unbindConnection(redisSessionTemplate.getConnectionFactory());
        }
        return object;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisSessionTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisSessionTemplate.getConnectionFactory());
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                try {
                    redisSessionTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
                } finally {
                    RedisConnectionUtils.unbindConnection(redisSessionTemplate.getConnectionFactory());
                }
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间及时间单位
     *
     * @param key      键
     * @param value    值
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisSessionTemplate.opsForValue().set(key, value, time, timeUnit);
                RedisConnectionUtils.unbindConnection(redisSessionTemplate.getConnectionFactory());
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
