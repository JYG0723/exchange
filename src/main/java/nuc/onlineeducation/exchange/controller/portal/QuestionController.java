package nuc.onlineeducation.exchange.controller.portal;

import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.HostHolder;
import nuc.onlineeducation.exchange.model.Question;
import nuc.onlineeducation.exchange.service.ICommentService;
import nuc.onlineeducation.exchange.service.IQuestionService;
import nuc.onlineeducation.exchange.vo.QuestionDetailVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private HostHolder hostHolder;

    @Autowired
    private ICommentService iCommentService;

    /**
     * 新增问题
     *
     * @param title   标题
     * @param content 内容
     * @return
     */
    @PostMapping("/add")
    public ServerResponse<Integer> questionSave(@RequestParam(value = "title") String title,
                                                @RequestParam(value = "content") String content) {// 拦截器 -> 登录用户权限
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setUserId(hostHolder.getUser().getId());
        question.setCommentCount(QUESTION_INIT_COMMENT_COUNT);
        question.setCreateTime(DateTime.now().toDate() );
        question.setUpdateTime(DateTime.now().toDate());
        return iQuestionService.saveQuestion(question);
    }

    @GetMapping("/{id}")
    public ServerResponse<QuestionDetailVO> questionDetail(@PathVariable(value = "id") Integer questionId) {
        return iQuestionService.getQuestionDetail(questionId);
    }
}
