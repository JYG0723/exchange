package nuc.onlineeducation.exchange.controller.portal;

import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.HostHolder;
import nuc.onlineeducation.exchange.model.Message;
import nuc.onlineeducation.exchange.model.Question;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.*;
import nuc.onlineeducation.exchange.util.PropertiesUtil;
import nuc.onlineeducation.exchange.vo.CommentVO;
import nuc.onlineeducation.exchange.vo.QuestionDetailVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 22:20 2018/1/9.
 */
@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    private static final Integer QUESTION_INIT_COMMENT_COUNT = 0;

    @Autowired
    private IQuestionService iQuestionService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private IMessageService iMessageService;

    @Autowired
    private ILikeService iLikeService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 新增问题
     *
     * @param title   标题
     * @param content 内容
     * @return
     */
    @PostMapping("/")
    public ServerResponse<Integer> questionSave(@RequestParam(value = "title") String title,
                                                @RequestParam(value = "content") String content) {// 拦截器 -> 登录用户权限
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setUserId(hostHolder.getUser().getId());
        question.setCommentCount(QUESTION_INIT_COMMENT_COUNT);
        question.setCreateTime(DateTime.now().toDate());
        question.setUpdateTime(DateTime.now().toDate());
        return iQuestionService.saveQuestion(question);
    }

    /**
     * 问题详情
     *
     * @param questionId 问题id
     * @return
     */
    @GetMapping("/{id}")
    public ServerResponse<QuestionDetailVO> questionDetail(@PathVariable(value = "id") Integer questionId) {
        ServerResponse serverResponse = iQuestionService.getQuestionDetail(questionId);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        QuestionDetailVO questionDetailVO = (QuestionDetailVO) serverResponse.getData();
        List<CommentVO> commentVOList = questionDetailVO.getCommentVOList();
        for (CommentVO commentVOItem : commentVOList
                ) {
//            49
            commentVOItem.setLiked(iLikeService.likeEntityStatus(hostHolder.getUser().getId(), commentVOItem
                    .getEntityType(), commentVOItem.getEntityId()).getData());
            commentVOItem.setLikeCount(iLikeService.getLikeCount(commentVOItem.getEntityType(), commentVOItem
                    .getEntityId()).getData());
        }
        return serverResponse;
    }

    /**
     * 邀请指定人回答 teacher/student
     *
     * @param toName 被邀请人
     * @return
     */
    @GetMapping("/invite")
    public ServerResponse inviteAnswer(@RequestParam("toName") String toName) {
        // 被邀请的用户
        User helpUser = iUserService.getUserByUsername(toName).getData();
        if (helpUser == null) {
            return ServerResponse.createByErrorMessage("该用户不存在");
        }
        User hardUser = hostHolder.getUser();

        Message message = new Message();
        message.setFromId(Const.ADMIN_ID);// 管理员的id
        message.setToId(helpUser.getId());
        // 亲爱的 - ***老师 - *** 邀请你回答问题
        message.setContent(
                PropertiesUtil.getProperty("exchange.greetings") +
                        helpUser.getUsername() + Const.UserRoleEnum.ROLE_TEACHER.getValue() +
                        " / " +
                        hardUser.getUsername() + PropertiesUtil.getProperty("question.invite.content")
        );
        message.setHasRead(Const.MessageStatus.UN_READ);
        return iMessageService.saveMessage(message);
    }
}
