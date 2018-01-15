package nuc.onlineeducation.exchange.service;

import nuc.onlineeducation.exchange.common.ServerResponse;

/**
 * @author Ji YongGuang.
 * @date 0:14 2018/1/15.
 */
public interface ILikeService {

    ServerResponse<Long> getLikeCount(Integer entityType, Integer entityId);

    ServerResponse<Integer> likeEntityStatus(Integer userId, Integer entityType, Integer entityId);

    ServerResponse<Long> like(Integer userId, Integer entityType, Integer entityId);

    ServerResponse<Long> disLike(Integer userId, Integer entityType, Integer entityId);
}
