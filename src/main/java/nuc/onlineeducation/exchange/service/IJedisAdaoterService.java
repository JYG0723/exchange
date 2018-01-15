package nuc.onlineeducation.exchange.service;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 9:17 2018/1/15.
 */
public interface IJedisAdaoterService {

    long sadd(String key, String value);

    long srem(String key, String value);

    long scard(String key);

    boolean sismember(String key, String value);

    long lpush(String key, String value);

    List<String> brpop(int timeout, String key);
}
