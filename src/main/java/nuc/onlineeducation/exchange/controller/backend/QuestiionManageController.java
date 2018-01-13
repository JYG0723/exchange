package nuc.onlineeducation.exchange.controller.backend;

import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.Question;
import nuc.onlineeducation.exchange.service.IQuestionService;
import nuc.onlineeducation.exchange.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ji YongGuang.
 * @date 14:56 2018/1/13.
 */
@RestController
@RequestMapping(value = "/manage/questions")
public class QuestiionManageController {

    @Autowired
    private IQuestionService iQuestionService;

    /**
     * 删除某个问题
     *
     * @param questionId 问题id
     * @return
     */
    @DeleteMapping("/{id}")
    public ServerResponse removeQuestion(@PathVariable("id") Integer questionId) {
        return iQuestionService.removeQuestionById(questionId);
    }

    /**
     * 获取全部问题 / 分页处理
     *
     * @param pageNum 页数
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/")
    public ServerResponse getQuestions(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer
                                               pageSize) {
        return iQuestionService.getQuestions(pageNum, pageSize);
    }

    /**
     * 更改问题信息
     * 1. 用户名 2 . 用户头像 3. 一句话介绍  涉及到用户信息不能改
     *
     * @param questionVO  questionVO
     * @return
     */
    @PutMapping("/change")
    public ServerResponse updateQuestion(QuestionVO questionVO) {
        // VO -> 用户名 用户头像 一句话介绍 不能改
        Question question = new Question();
        question.setId(questionVO.getId());
        question.setContent(questionVO.getContent());
        question.setTitle(questionVO.getContent());
        question.setUserId(questionVO.getUserId());
        question.setCommentCount(questionVO.getCommentCount());
        question.setCreateTime(question.getCreateTime());
        question.setUpdateTime(question.getUpdateTime());
        return iQuestionService.updateQuestion(question);
    }
}
