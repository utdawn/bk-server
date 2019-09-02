package com.gcwl.bkserver.service;

import com.gcwl.bkserver.util.RedisUtil;
import com.gcwl.bkserver.util.RedisUtil2;
import com.gcwl.bkserver.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@Component
public class JedisCache {

//    @Autowired
//    private JedisPool jedisPool;

//    private Jedis jedis = jedisPool.getResource();

    /**
     * hash散列存储
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hset(String key, String field, String value){
//        Jedis jedis = jedisPool.getResource();
        Jedis jedis = RedisUtil2.getJedis();
        Long result = 0L;
        try{
            result = jedis.hset(key, field, value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            jedis.close();
            RedisUtil2.returnResource(jedis);
        }
        return result;
    }

    /**
     * hash散列取值
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field){
//        Jedis jedis = jedisPool.getResource();
        Jedis jedis = RedisUtil2.getJedis();
        String result = "";
        try{
            result = jedis.hget(key, field);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            jedis.close();
            RedisUtil2.returnResource(jedis);
        }
        return result;
    }

    /**
     *  hash获取key下的所有键值对
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key){
//        Jedis jedis = jedisPool.getResource();
        Jedis jedis = RedisUtil2.getJedis();
        Map<String, String> result = null;
        try{
            result = jedis.hgetAll(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            jedis.close();
            RedisUtil2.returnResource(jedis);
        }
        return result;
    }

    /**
     * hash判断key下的field是否存在
     * @param key
     * @param field
     * @return
     */
    public boolean hexists(String key, String field){
//        Jedis jedis = jedisPool.getResource();
        Jedis jedis = RedisUtil2.getJedis();
        Boolean result = null;
        try{
            result = jedis.hexists(key, field);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            jedis.close();
            RedisUtil2.returnResource(jedis);
        }
        return result;
    }

    /**
     * hash移除对应key下field对应的值
     * @param key
     * @param fields
     * @return
     */
    public Long hdel(String key, String... fields){
//        Jedis jedis = jedisPool.getResource();
        Jedis jedis = RedisUtil2.getJedis();
        Long result = null;
        try{
            result = jedis.hdel(key, fields);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            jedis.close();
            RedisUtil2.returnResource(jedis);
        }
        return result;
    }

    /**
     * hash移除对应key
     * @param key
     * @return
     */
    public Long delKey(String key){
//        Jedis jedis = jedisPool.getResource();
        Jedis jedis = RedisUtil2.getJedis();
        Long result = null;
        try{
            result = jedis.del(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            jedis.close();
            RedisUtil2.returnResource(jedis);
        }
        return result;
    }
//
//    /**
//     * 存储字符串键值对
//     *
//     * @param key
//     * @param value
//     * @return
//     * @author hw
//     * @date 2018年12月14日
//     */
//    public String set(String key, String value) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.set(key, value);
//        } catch (Exception e) {
//            e.printStackTrace();
////            throw new SysException(e.getMessage());
//        } finally {
//            jedis.close();
//        }
//        return null;
//    }
//
//    /**
//     * 得到对应键的字符串值
//     *
//     * @param key
//     * @return
//     * @author hw
//     * @date 2018年12月14日
//     */
//    public String get(String key) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.get(key);
//        } catch (Exception e) {
//            e.printStackTrace();
////            throw new SysException(e.getMessage());
//        } finally {
//            jedis.close();
//        }
//        return null;
//    }
//
//    /**
//     * 删除字符串键值对
//     *
//     * @param key
//     * @return
//     * @author hw
//     * @date 2018年12月14日
//     */
//    public Long del(String key) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.del(key);
//        } catch (Exception e) {
//            e.printStackTrace();
////            throw new SysException(e.getMessage());
//        } finally {
//            jedis.close();
//        }
//        return null;
//    }
//
//    /**
//     * 存储对象
//     *
//     * @param key
//     * @param value
//     * @return
//     * @author hw
//     * @date 2018年12月14日
//     */
//    public String setObject(String key, Object value) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.set(key.getBytes(), SerializeUtil.serialize(value));
//        } catch (Exception e) {
//            e.printStackTrace();
////            throw new SysException(e.getMessage());
//        } finally {
//            jedis.close();
//        }
//        return null;
//    }
//
//    /**
//     * 得到对应键的对象
//     *
//     * @param key
//     * @return
//     * @author hw
//     * @date 2018年12月14日
//     */
//    public Object getObject(String key) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            byte[] byteArr = jedis.get(key.getBytes());
//            return SerializeUtil.unSerialize(byteArr);
//        } catch (Exception e) {
//            e.printStackTrace();
////            throw new SysException(e.getMessage());
//        } finally {
//            jedis.close();
//        }
//        return null;
//    }

}