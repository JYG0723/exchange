package nuc.onlineeducation.exchange.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import nuc.onlineeducation.exchange.common.ResponseCodeEnum;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.dao.CommentMapper;
import nuc.onlineeducation.exchange.dao.UserMapper;
import nuc.onlineeducation.exchange.model.Comment;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.ICommentService;
import nuc.onlineeducation.exchange.service.ISensitiveService;
import nuc.onlineeducation.exchange.util.DateTimeUtil;
import nuc.onlineeducation.exchange.vo.CommentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 12:54 2018/1/11.
 */
@Service("iCommentService")
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ISensitiveService iSensitiveService;

    @Override
    public ServerResponse saveComment(Comment comment) {
        if (comment == null) {
            return ServerResponse.createByErrorMessage("新增评论失败");
        }
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(iSensitiveService.filter(comment.getContent()));
        int result = commentMapper.insert(comment);
        if (result > 0) {
            return ServerResponse.createBySuccess("添加评论成功", comment.getId());
        }
        return ServerResponse.createByErrorMessage("新增评论失败");
    }

    @Override
    public ServerResponse<PageInfo> getCommentsByEntity(Integer entityId, Integer entityType, Integer pageNum,
                                                        Integer pageSize) {
        if (StringUtils.isBlank(entityId.toString()) || StringUtils.isBlank(entityType.toString())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(),
                    ResponseCodeEnum.ILLEGAL_ARGUEMENT.getDesc());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectCommentsByEntity(entityId, entityType);

        List<CommentVO> commentVOList = Lists.newArrayList();
        for (Comment commentItem : comments) {
            CommentVO commentVO = assemableCommentVO(commentItem);
            commentVOList.add(commentVO);
        }

        PageInfo pageResult = new PageInfo(comments);
        pageResult.setList(commentVOList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse getCommentCount(Integer entityId, Integer entityType) {
        if (StringUtils.isBlank(entityId.toString()) || StringUtils.isBlank(entityType.toString())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(),
                    ResponseCodeEnum
                            .ILLEGAL_ARGUEMENT.getDesc());
        }
        int commentCount = commentMapper.getCommentCountByEntity(entityId, entityType);
        return ServerResponse.createBySuccess(commentCount);
    }

    @Override
    public ServerResponse removeCommentById(Integer commentId) {
        if (StringUtils.isBlank(commentId.toString())) {
            return ServerResponse.createByErrorMessage("评论ID不能为空");
        }
        int result = commentMapper.deleteByPrimaryKey(commentId);
        if (result > 0) {
            return ServerResponse.createByErrorMessage("评论删除成功");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<PageInfo> getComments(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectComments();
        List<CommentVO> commentVOList = Lists.newArrayList();

        for (Comment CommentItem : comments) {
            CommentVO commentVO = assemableCommentVO(CommentItem);
            commentVOList.add(commentVO);
        }
        PageInfo pageResult = new PageInfo(comments);
        pageResult.setList(commentVOList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse updateComment(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(iSensitiveService.filter(comment.getContent()));
        int result = commentMapper.updateByPrimaryKeySelective(comment);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("评论详情更改成功");
        }
        return ServerResponse.createByErrorMessage("评论详情更改失败");
    }

    @Override
    public ServerResponse<Comment> getCommentById(Integer commentId) {
        if (StringUtils.isBlank(commentId.toString())) {
            return ServerResponse.createByErrorMessage("评论的id不能为空");
        }
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        return ServerResponse.createBySuccess(comment);
    }

    private CommentVO assemableCommentVO(Comment comment) {
        CommentVO commentVO = new CommentVO();
        commentVO.setId(comment.getId());
        commentVO.setContent(comment.getContent());
        commentVO.setEntityId(comment.getEntityId());
        commentVO.setEntityType(comment.getEntityType());
        commentVO.setCreateTime(DateTimeUtil.dateToStr(comment.getCreateTime()));
        commentVO.setUpdateTime(DateTimeUtil.dateToStr(comment.getUpdateTime()));
        commentVO.setStatus(comment.getStatus());

        User user = userMapper.selectByPrimaryKey(comment.getUserId());
        commentVO.setUserId(comment.getUserId());
        commentVO.setUsername(user.getUsername());
        commentVO.setHeadUrl(user.getHeadUrl());
        commentVO.setIntroduce(user.getIntroduce());
        return commentVO;
    }
}
