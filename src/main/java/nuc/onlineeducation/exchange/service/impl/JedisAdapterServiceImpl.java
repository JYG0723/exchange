package nuc.onlineeducation.exchange.service.impl;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import nuc.onlineeducation.exchange.service.IJedisAdaoterService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 23:42 2018/1/14.
 */
@Log4j2
@Service(value = "iJedisAdaoterService")
public class JedisAdapterServiceImpl implements InitializingBean, IJedisAdaoterService {

    private static JedisPool jedisPool;

    /**
     * 向集合中插入数据
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public long sadd(String key, String value) {
        @Cleanup Jedis jedis = null;
        jedis = jedisPool.getResource();
        return jedis.sadd(key, value); // 进程状态码回复 or 字节数 ?
    }

    /**
     * 删除集合中的某条数据
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public long srem(String key, String value) {
        @Cleanup Jedis jedis = null;
        jedis = jedisPool.getResource();
        return jedis.srem(key, value);
    }

    /**
     * 查询某个集合中的元素数量
     *
     * @param key
     * @return
     */
    @Override
    public long scard(String key) {
        @Cleanup Jedis jedis = null;
        jedis = jedisPool.getResource();
        return jedis.scard(key);
    }

    /**
     * 查询某一元素是否是该集合中的元素
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean sismember(String key, String value) {
        @Cleanup Jedis jedis = null;
        jedis = jedisPool.getResource();
        return jedis.sismember(key, value);
    }

    /**
     * 将某个元素添加到队列中
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public long lpush(String key, String value) {
        @Cleanup Jedis jedis = null;
        jedis = jedisPool.getResource();
        return jedis.lpush(key, value);
    }

    /**
     * 从某个队列中弹出一个元素
     *
     * @param timeout
     * @param key
     * @return
     */
    @Override
    public List<String> brpop(int timeout, String key) {// brpop先进先出。blpop先进后出
        @Cleanup Jedis jedis = null;
        jedis = jedisPool.getResource();
        return jedis.brpop(timeout, key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool("redis://127.0.0.1:6379/10");
    }
}
