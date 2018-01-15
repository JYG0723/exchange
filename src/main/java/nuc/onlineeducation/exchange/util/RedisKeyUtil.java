package nuc.onlineeducation.exchange.util;

/**
 * @author Ji YongGuang.
 * @date 0:17 2018/1/15.
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String BIZ_LIKE = "LIKE";
    private static final String BIZ_DISLIKE = "DISLIKE";
    private static final String BIZ_EVENTQUEUE = "EVENT_QUEUE";

    /**
     * 生成实体对应的赞的唯一Key值
     *
     * @param entityType 实体类型
     * @param entityId   实体id
     * @return
     */
    public static String getLikeKey(int entityType, int entityId) {
        // LIKE:2:1
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    /**
     * 生成实体对应的踩的唯一Key值
     *
     * @param entityType 实体类型
     * @param entityId   实体id
     * @return
     */
    public static String getDisLikeKey(int entityType, int entityId) {
        // DISLIKE:2:1
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    /**
     * 生成该队列的唯一Key值。该队列中所有的事件都是该Key的Value
     *
     * @return
     */
    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }
}
