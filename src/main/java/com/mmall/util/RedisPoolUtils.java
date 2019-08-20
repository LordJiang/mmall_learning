package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisPoolUtils {

    /**
     * 设置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key, int exTime){
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            //由于通过loombook进行了日志的配置，可以将错误信息记录在日志里面
            //e.printStackTrace();
            //这里不采用error:{} e.getMessage()的原因是：采用这种格式记录的不是e的详细信息，并不利于异常或者错误排查
            log.error("set key:{} value:{} error",key,e);
            //由于这里出现异常，那么该jedis连接就应该放入Broken
            RedisPool.returnBrokenResource(jedis);
            //这时返回的result为null，那么上层逻辑就可以根据result进行判断
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //exTime的单位是秒
    public static String setEx(String key, String value, int exTime){
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            //由于通过loombook进行了日志的配置，可以将错误信息记录在日志里面
            //e.printStackTrace();
            //这里不采用error:{} e.getMessage()的原因是：采用这种格式记录的不是e的详细信息，并不利于异常或者错误排查
            log.error("set key:{} value:{} error",key,value,e);
            //由于这里出现异常，那么该jedis连接就应该放入Broken
            RedisPool.returnBrokenResource(jedis);
            //这时返回的result为null，那么上层逻辑就可以根据result进行判断
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String set(String key, String value){
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            //由于通过loombook进行了日志的配置，可以将错误信息记录在日志里面
            //e.printStackTrace();
            //这里不采用error:{} e.getMessage()的原因是：采用这种格式记录的不是e的详细信息，并不利于异常或者错误排查
            log.error("set key:{} value:{} error",key,value,e);
            //由于这里出现异常，那么该jedis连接就应该放入Broken
            RedisPool.returnBrokenResource(jedis);
            //这时返回的result为null，那么上层逻辑就可以根据result进行判断
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String get(String key){
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            //由于通过loombook进行了日志的配置，可以将错误信息记录在日志里面
            //e.printStackTrace();
            //这里不采用error:{} e.getMessage()的原因是：采用这种格式记录的不是e的详细信息，并不利于异常或者错误排查
            log.error("get key:{} error",key,e);
            //由于这里出现异常，那么该jedis连接就应该放入Broken
            RedisPool.returnBrokenResource(jedis);
            //这时返回的result为null，那么上层逻辑就可以根据result进行判断
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            //由于通过loombook进行了日志的配置，可以将错误信息记录在日志里面
            //e.printStackTrace();
            //这里不采用error:{} e.getMessage()的原因是：采用这种格式记录的不是e的详细信息，并不利于异常或者错误排查
            log.error("get key:{} error",key,e);
            //由于这里出现异常，那么该jedis连接就应该放入Broken
            RedisPool.returnBrokenResource(jedis);
            //这时返回的result为null，那么上层逻辑就可以根据result进行判断
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();

        RedisPoolUtils.set("keyTest", "value");

        String value = RedisPoolUtils.get("keyTest");

        RedisPoolUtils.setEx("keyex","valueex",60*10);

        RedisPoolUtils.expire("keyTest",60*20);

        RedisPoolUtils.del("keyTest");

        System.out.println("end");
    }
}
