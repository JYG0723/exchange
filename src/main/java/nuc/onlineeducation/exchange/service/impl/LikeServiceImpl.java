package nuc.onlineeducation.exchange.service.impl;

import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.service.IJedisAdaoterService;
import nuc.onlineeducation.exchange.service.ILikeService;
import nuc.onlineeducation.exchange.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ji YongGuang.
 * @date 0:14 2018/1/15.
 */
@Service(value = "iLikeService")
public class LikeServiceImpl implements ILikeService {

    private static final Integer LIKE_ = 1;
    private static final Integer DISLIKE_ = -1;
    private static final Integer NOFEEL = 0;

    @Autowired
    private IJedisAdaoterService iJedisAdaoterService;

    /**
     * 获取某个实体被喜欢的数量
     *
     * @param entityType
     * @param entityId
     * @return
     */
    @Override
    public ServerResponse<Long> getLikeCount(Integer entityType, Integer entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        long likeCount = iJedisAdaoterService.scard(likeKey);
        return ServerResponse.createBySuccess("该实体被喜欢的数量", likeCount);
    }

    /**
     * 对一个实体的喜欢状态
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    @Override
    public ServerResponse<Integer> likeEntityStatus(Integer userId, Integer entityType, Integer entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (iJedisAdaoterService.sismember(likeKey, String.valueOf(userId))) {
            return ServerResponse.createBySuccess("用户对该实体的喜欢状态", LIKE_);
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        // 不喜欢就返回-1 无感则为-0
        int feel = iJedisAdaoterService.sismember(disLikeKey, String.valueOf(userId)) ? DISLIKE_ : NOFEEL;
        return ServerResponse.createBySuccess("用户对该实体的喜欢状态", feel);
    }

    /**
     * 点赞
     *
     * @param userId     用户id
     * @param entityId   实体id
     * @param entityType 实体类型
     * @return
     */
    @Override
    public ServerResponse<Long> like(Integer userId, Integer entityType, Integer entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        iJedisAdaoterService.sadd(likeKey, String.valueOf(userId));
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        iJedisAdaoterService.srem(disLikeKey, String.valueOf(userId));
        Long likeCount = iJedisAdaoterService.scard(likeKey);
        return ServerResponse.createBySuccess("点赞后实体的喜欢数量", likeCount);
    }

    /**
     * 点踩
     *
     * @param userId     用户id
     * @param entityId   实体id
     * @param entityType 实体类型
     * @return
     */
    @Override
    public ServerResponse<Long> disLike(Integer userId, Integer entityType, Integer entityId) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        iJedisAdaoterService.sadd(disLikeKey, String.valueOf(userId));
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        iJedisAdaoterService.srem(likeKey, String.valueOf(userId));
        Long likeCount = iJedisAdaoterService.scard(likeKey);
        return ServerResponse.createBySuccess("点踩后实体的喜欢数量", likeCount);
    }
}
