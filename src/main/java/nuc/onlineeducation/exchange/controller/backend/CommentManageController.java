package nuc.onlineeducation.exchange.controller.backend;

import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.Comment;
import nuc.onlineeducation.exchange.service.ICommentService;
import nuc.onlineeducation.exchange.util.DateTimeUtil;
import nuc.onlineeducation.exchange.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ji YongGuang.
 * @date 16:08 2018/1/13.
 */
@RestController
@RequestMapping(value = "/manage/comments")
public class CommentManageController {

    @Autowired
    private ICommentService iCommentService;

    /**
     * 删除某个评论
     *
     * @param commentId 评论id
     * @return
     */
    @DeleteMapping("/{id}")
    public ServerResponse removeComment(@PathVariable("id") Integer commentId) {
        return iCommentService.removeCommentById(commentId);
    }

    /**
     * 获取全部评论 / 分页处理
     *
     * @param pageNum  页数
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/")
    public ServerResponse getComments(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer
                                              pageSize) {
        return iCommentService.getComments(pageNum, pageSize);
    }

    /**
     * 更改评论详情
     * 1. 用户名 2 . 用户头像 3. 一句话介绍  涉及到用户信息不能改
     *
     * @param commentVO commentVO
     * @return
     */
    @PutMapping("/change")
    public ServerResponse updateComment(CommentVO commentVO) {
        // VO -> 用户名 用户头像 一句话介绍 不能改
        Comment comment = new Comment();
        comment.setId(commentVO.getId());
        comment.setContent(commentVO.getContent());
        comment.setStatus(commentVO.getStatus());
        comment.setUserId(commentVO.getUserId());
        comment.setEntityId(commentVO.getEntityId());
        comment.setEntityType(commentVO.getEntityType());
        comment.setCreateTime(DateTimeUtil.strToDate(commentVO.getCreateTime()));
        comment.setUpdateTime(DateTimeUtil.strToDate(commentVO.getUpdateTime()));
        return iCommentService.updateComment(comment);
    }
}
