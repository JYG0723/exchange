package nuc.onlineeducation.exchange.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ResponseCodeEnum;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.dao.QuestionMapper;
import nuc.onlineeducation.exchange.dao.UserMapper;
import nuc.onlineeducation.exchange.model.Question;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.ICommentService;
import nuc.onlineeducation.exchange.service.IQuestionService;
import nuc.onlineeducation.exchange.service.ISensitiveService;
import nuc.onlineeducation.exchange.util.DateTimeUtil;
import nuc.onlineeducation.exchange.vo.CommentVO;
import nuc.onlineeducation.exchange.vo.QuestionDetailVO;
import nuc.onlineeducation.exchange.vo.QuestionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 0:08 2018/1/8.
 */
@Service(value = "iQuestionService")
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ISensitiveService iSensitiveService;

    @Autowired
    private ICommentService iCommentService;

    @Override
    public ServerResponse<Integer> saveQuestion(Question question) {
        if (question != null) {
            // TML标签过滤H -> 转义
            question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
            question.setContent(HtmlUtils.htmlEscape(question.getContent()));
            // 敏感词过滤
            question.setTitle(iSensitiveService.filter(question.getTitle()));
            question.setContent(iSensitiveService.filter(question.getContent()));
            int result = questionMapper.insert(question);
            if (result > 0) {
                return ServerResponse.createBySuccess("新增问题成功", question.getId());
            }
        }
        return ServerResponse.createByErrorMessage("插入问题失败");
    }

    @Override
    public ServerResponse<PageInfo> getLatestQuestions(Integer userId, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(userId.toString())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(), "用户id不能为空");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.selectListByUserId(userId);
        List<QuestionVO> questionVOList = Lists.newArrayList();
        for (Question questionItem : questions) {
            QuestionVO questionVO = assembleQuestionVO(questionItem);
            questionVOList.add(questionVO);
        }
        PageInfo pageResult = new PageInfo(questions);
        pageResult.setList(questionVOList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<QuestionDetailVO> getQuestionDetail(Integer questionId) {
        if (StringUtils.isBlank(questionId.toString())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(), "问题id不能为空");
        }
        ServerResponse serverResponse = iCommentService.getCommentsByEntity(questionId, Const.CommentEntityTypeEnum
                .QUESTION.getCode(), 0, 10);// 默认第0页 10条记录
        PageInfo pageInfo = (PageInfo) serverResponse.getData();
        List<CommentVO> commentVOList = pageInfo.getList();

        Question question = questionMapper.selectByPrimaryKey(questionId);
        return ServerResponse.createBySuccess(assembleQuestionDetailVO(question, commentVOList));
    }

    @Override
    public ServerResponse updateCommentCount(Integer entityId, Integer count) {
        if (StringUtils.isBlank(entityId.toString()) || StringUtils.isBlank(count.toString())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(),
                    ResponseCodeEnum.ILLEGAL_ARGUEMENT.getDesc());
        }
        int result = questionMapper.updateCommentCount(entityId, count);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("评论数修改成功");
        }
        return ServerResponse.createByErrorMessage("更新评论数量失败");
    }

    @Override
    public ServerResponse removeQuestionById(Integer questionId) {
        if (StringUtils.isBlank(questionId.toString())) {
            return ServerResponse.createByErrorMessage("问题的id不能为空");
        }
        // 尽量别删
        int result = questionMapper.deleteByPrimaryKey(questionId);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("问题删除失败");
    }

    @Override
    public ServerResponse<PageInfo> getQuestions(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.selectList();
        List<QuestionVO> questionVOList = Lists.newArrayList();
        for (Question questionItem : questions) {
            QuestionVO questionVO = assembleQuestionVO(questionItem);
            questionVOList.add(questionVO);
        }
        PageInfo pageResult = new PageInfo(questions);
        pageResult.setList(questionVOList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse updateQuestion(Question question) {
        int result = questionMapper.updateByPrimaryKeySelective(question);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("问题详情更改成功");
        }
        return ServerResponse.createByErrorMessage("问题详情更改失败");
    }

    private QuestionVO assembleQuestionVO(Question question) {
        QuestionVO questionVO = new QuestionVO();
        User user = userMapper.selectByPrimaryKey(question.getUserId());
        questionVO.setUserId(user.getId());
        questionVO.setUsername(user.getUsername());
        questionVO.setIntroduce(user.getIntroduce());
        questionVO.setUserHeadUrl(user.getHeadUrl());

        questionVO.setId(question.getId());
        questionVO.setTitle(question.getTitle());
        questionVO.setContent(question.getContent());
        questionVO.setCommentCount(question.getCommentCount());
        questionVO.setCreateTime(DateTimeUtil.dateToStr(question.getCreateTime()));
        questionVO.setUpdateTime(DateTimeUtil.dateToStr(question.getUpdateTime()));
        return questionVO;
    }

    private QuestionDetailVO assembleQuestionDetailVO(Question question, List<CommentVO> commentVOList) {
        QuestionDetailVO questionDetailVO = new QuestionDetailVO();
        User user = userMapper.selectByPrimaryKey(question.getUserId());
        questionDetailVO.setUserId(user.getId());
        questionDetailVO.setUsername(user.getUsername());
        questionDetailVO.setIntroduce(user.getIntroduce());
        questionDetailVO.setUserHeadUrl(user.getHeadUrl());

        questionDetailVO.setId(question.getId());
        questionDetailVO.setTitle(question.getTitle());
        questionDetailVO.setContent(question.getContent());
        questionDetailVO.setCommentCount(question.getCommentCount());
        questionDetailVO.setCreateTime(DateTimeUtil.dateToStr(question.getCreateTime()));
        questionDetailVO.setUpdateTime(DateTimeUtil.dateToStr(question.getUpdateTime()));

        questionDetailVO.setCommentVOList(commentVOList);
        return questionDetailVO;
    }

}
