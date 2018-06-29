
package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.example.demo.service.RedisService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author  dell
 * @create  2018-06-29 12:01
 * @desc    redis常用方法实现类
 **/

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, ?> redisTemplate;

/**
     *  @author Pre_fantasy
     *  @create 2018/6/28 20:26
     *  @param  {String  key, String value}
     *  @return boolean
     *  @desc   设置给定key的值，如果key已经储存其他值，set就覆盖旧值且无视类型
     */
    @Override
    public boolean set(String key, String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

/*ps -ef|grep hkd*/



    /**
     *  @author Pre_fantasy
     *  @create 2018/6/28 20:30
     *  @param  {String key}
     *  @return String
     *  @desc   获取指定key的值，如果key不存在，返回null。如果key储存的值不是字符串，返回一个错误。
     */

    @Override
    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

/**
     *  @author Pre_fantasy
     *  @create 2018/6/28 20:38
     *  @param  {String key, long expire}
     *  @return boolean
     *  @desc   设置key的过期时间。key过期后不可再用
     */

    @Override
    public boolean expire(String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }


/**
     *  @author Pre_fantasy
     *  @create 2018/6/28 20:40
     *  @param  {String key, List<T> list}
     *  @return boolean
     *  @desc   存储集合
     */

    @Override
    public <T> boolean setList(String key, List<T> list) {
        String value = JSON.toJSONString(list);
        return set(key, value);
    }

/**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:30
     *  @param  {String key, Class<T> clz
     *  @return List<T>
     *  @desc   同过key获取List<T>
     */

    @Override
    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            List<T> list = JSON.parseArray(json, clz);
            return list;
        }
        return null;
    }



/**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:34
     *  @param  {String key, Object obj}
     *  @return long
     *  @desc   将一个或多个值掺入到 列表头部， 如果key不存在，一个空列表会被创建并执行lpush操作。
     *          当key存在但不是列表类，返回一个错误
     */

    @Override
    public long lpush(final String key, Object obj) {
        final String value = JSON.toJSONString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:39
     *  @param  {String key, Object obj}
     *  @return long
     *  @desc   将一个或多个值插入到类表的尾部（最右边）
     *          如果列表不存在，一个空列表会被创建出来并且执行rpush，当列表存在但不是列表类型时， 返回一个错误。
     */

    @Override
    public long rpush(final String key, Object obj) {
        final String value = JSON.toJSONString(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }


    /**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:52
     *  @param  {String key}
     *  @return String
     *  @desc   移除并返回列表的第一个元素
     */

    @Override
    public String lpop(String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res = connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
        });
        return result;
    }


    /**
     *  @author Pre_fantasy
     *  @create 2018/6/29 11:53
     *  @param  {final String key}
     *  @return long
     *  @desc   删除已存在的键。 不存在的key会被忽略。
     */

    @Override
    public long del(String key) {
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long value = connection.del(serializer.serialize(key));
                return value;
            }
        });
        return result;
    }
}

