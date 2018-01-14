package nuc.onlineeducation.exchange.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ResponseCodeEnum;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.dao.MessageMapper;
import nuc.onlineeducation.exchange.dao.UserMapper;
import nuc.onlineeducation.exchange.model.HostHolder;
import nuc.onlineeducation.exchange.model.Message;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.IMessageService;
import nuc.onlineeducation.exchange.service.ISensitiveService;
import nuc.onlineeducation.exchange.util.DateTimeUtil;
import nuc.onlineeducation.exchange.vo.MessageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 19:25 2018/1/11.
 */
@Log4j2
@Service(value = "iMessageService")
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ISensitiveService iSensitiveService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public ServerResponse<Integer> saveMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(iSensitiveService.filter(message.getContent()));
        int result = messageMapper.insert(message);
        if (result > 0) {
            return ServerResponse.createBySuccess("新增消息成功", message.getId());// 应该为空
        }
        return ServerResponse.createByErrorMessage("添加信息失败");
    }

    @Override
    public ServerResponse<PageInfo> getConversationDetail(String conversationId, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(conversationId) || StringUtils.isBlank(pageNum.toString()) || StringUtils.isBlank
                (pageSize.toString())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(),
                    ResponseCodeEnum.ILLEGAL_ARGUEMENT.getDesc());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messageList = messageMapper.getConversationDetail(conversationId);// 把这个集合中的内容全部设置为已读
        this.updateHasRead(messageList);// 信息设置为已读
        List<MessageVO> messageVOList = Lists.newArrayList();
        for (Message messageItem : messageList) {
            MessageVO messageVO = assembleMessageVO(messageItem);
            messageVOList.add(messageVO);
        }

        PageInfo pageInfo = new PageInfo(messageList);
        pageInfo.setList(messageVOList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> getConversationList(Integer userId, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(userId.toString()) || StringUtils.isBlank(pageNum.toString()) || StringUtils.isBlank
                (pageSize.toString())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(),
                    ResponseCodeEnum.ILLEGAL_ARGUEMENT.getDesc());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messageList = messageMapper.getConversationList(userId);

        List<MessageVO> messageVOList = Lists.newArrayList();
        for (Message messageItem : messageList) {
            MessageVO messageVO = assembleMessageVO(messageItem);
            messageVOList.add(messageVO);
        }
        PageInfo pageInfo = new PageInfo(messageList);
        pageInfo.setList(messageVOList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<Integer> getConversationUnreadCount(Integer userId, String conversationId) {
        if (StringUtils.isBlank(userId.toString()) || StringUtils.isBlank(conversationId)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(),
                    ResponseCodeEnum.ILLEGAL_ARGUEMENT.getDesc());
        }
        int result = messageMapper.getConversationUnreadCount(userId, conversationId);
        return ServerResponse.createBySuccess(result);
    }

    @Override
    public ServerResponse removeMessageById(Integer messageId) {
        if (StringUtils.isBlank(messageId.toString())) {
            return ServerResponse.createByErrorMessage("评论ID不能为空");
        }
        int result = messageMapper.deleteByPrimaryKey(messageId);
        if (result > 0) {
            return ServerResponse.createByErrorMessage("评论删除成功");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<PageInfo> getMessages(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = messageMapper.selectMessages();
        List<MessageVO> messageVOList = Lists.newArrayList();

        for (Message messageItem : messages) {
            MessageVO messageVO = assembleMessageVO(messageItem);
            messageVOList.add(messageVO);
        }
        PageInfo pageResult = new PageInfo(messages);
        pageResult.setList(messageVOList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse updateMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(iSensitiveService.filter(message.getContent()));
        int result = messageMapper.updateByPrimaryKeySelective(message);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("消息详情更改成功");
        }
        return ServerResponse.createByErrorMessage("消息详情更改失败");
    }

    private void updateHasRead(List<Message> messageList) {
        if (CollectionUtils.isEmpty(messageList)) {
            log.warn("用户的messageList为空");
        }
        for (Message messageItem : messageList) {
            messageMapper.updateHasRead(messageItem.getId());
        }
    }

    private MessageVO assembleMessageVO(Message message) {
        MessageVO messageVO = new MessageVO();
        messageVO.setId(message.getId());
        messageVO.setFromId(message.getFromId());
        messageVO.setToId(message.getToId());
        messageVO.setContent(message.getContent());
        messageVO.setConversationId(message.getConversationId());
        messageVO.setUnReadCount(this.getConversationUnreadCount(49, message
                .getConversationId()).getData());
        messageVO.setHasRead(Const.MessageStatus.HAS_READ);// 可以视为已读
        messageVO.setCreateTime(DateTimeUtil.dateToStr(message.getCreateTime()));
        messageVO.setUpdateTime(DateTimeUtil.dateToStr(message.getUpdateTime()));

        int targetId = message.getFromId();
                /*message.getFromId().intValue() == hostHolder.getUser().getId().intValue() ? message.getToId()
                .intValue() : message.getFromId().intValue();*/
        User user = userMapper.selectByPrimaryKey(targetId);
        messageVO.setUserId(user.getId());
        messageVO.setUsername(user.getUsername());
        messageVO.setHeadUrl(user.getHeadUrl());
        return messageVO;
    }
}
