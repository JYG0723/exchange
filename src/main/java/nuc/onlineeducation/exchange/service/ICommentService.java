package nuc.onlineeducation.exchange.service;

import com.github.pagehelper.PageInfo;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.Comment;

/**
 * @author Ji YongGuang.
 * @date 12:54 2018/1/11.
 */
public interface ICommentService {

    ServerResponse<PageInfo> getCommentsByEntity(Integer entityId, Integer entityType, Integer pageNum,
                                                 Integer pageSize);

    ServerResponse saveComment(Comment comment);

    ServerResponse getCommentCount(Integer entityId, Integer entityType);

    ServerResponse removeCommentById(Integer commentId);

    ServerResponse<PageInfo> getComments(Integer pageNum, Integer pageSize);

    ServerResponse updateComment(Comment comment);
}
