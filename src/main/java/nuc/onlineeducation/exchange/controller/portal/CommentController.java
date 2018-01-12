package nuc.onlineeducation.exchange.controller.portal;

import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ResponseCodeEnum;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.Comment;
import nuc.onlineeducation.exchange.model.HostHolder;
import nuc.onlineeducation.exchange.service.ICommentService;
import nuc.onlineeducation.exchange.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ji YongGuang.
 * @date 1:07 2018/1/10.
 */
@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IQuestionService iQuestionService;

    @PostMapping(value = "/add")
    public ServerResponse commentSave(@RequestParam(value = "entityId") Integer entityId,
                                      @RequestParam(value = "entityType") Integer entityType,
                                      @RequestParam(value = "content") String content) {
        Comment comment = new Comment();
        comment.setEntityId(entityId);
        comment.setEntityType(entityType);
        comment.setContent(content);
//        comment.setUserId(49);
        if (hostHolder.getUser() != null) {
            comment.setUserId(hostHolder.getUser().getId());
        } else {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(), ResponseCodeEnum
                    .NEED_LOGIN.getDesc());
        }
        comment.setStatus(Const.CommentStatus.COMMENT_VISIBLE);
        iCommentService.saveComment(comment);// commentId

        ServerResponse countResponse = iCommentService.getCommentCount(entityId, entityType);// commentCount
        return iQuestionService.updateCommentCount(comment.getEntityId(), (Integer) countResponse.getData());
    }

}