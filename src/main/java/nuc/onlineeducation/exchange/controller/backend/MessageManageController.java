package nuc.onlineeducation.exchange.controller.backend;

import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.Message;
import nuc.onlineeducation.exchange.service.IMessageService;
import nuc.onlineeducation.exchange.util.DateTimeUtil;
import nuc.onlineeducation.exchange.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ji YongGuang.
 * @date 16:49 2018/1/13.
 */
@RestController
@RequestMapping(value = "/manage/messages")
public class MessageManageController {

    @Autowired
    private IMessageService iMessageService;

    /**
     * 删除某个消息
     *
     * @param messageId 消息id
     * @return
     */
    @DeleteMapping("/{id}")
    public ServerResponse removeMessage(@PathVariable("id") Integer messageId) {
        return iMessageService.removeMessageById(messageId);
    }

    /**
     * 获取全部消息 / 分页处理
     *
     * @param pageNum 页数
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/")
    public ServerResponse getMessages(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer
                                              pageSize) {
        return iMessageService.getMessages(pageNum, pageSize);
    }

    /**
     * 查看消息详情
     *
     * @param messageId 消息id
     * @return
     */
    @GetMapping("/{id}")
    public ServerResponse<MessageVO> messageDetail(@PathVariable(value = "id") Integer messageId) {
        return iMessageService.getMessageById(messageId);
    }


    /**
     * 更改消息详情
     * 1. 用户名 2 . 用户头像 3. 用户id  涉及到用户信息不能改
     * 4. 用户消息未读数 不能修改
     *
     * @param MessageVO  MessageVO
     * @return
     */
    @PutMapping("/change")
    public ServerResponse updateMessage(MessageVO MessageVO) {
        // VO -> 用户名 用户头像 一句话介绍 不能改
        Message message = new Message();
        message.setId(MessageVO.getId());
        message.setContent(MessageVO.getContent());
        message.setHasRead(MessageVO.getHasRead());
        message.setContent(MessageVO.getContent());
        message.setConversationId(MessageVO.getConversationId());
        message.setFromId(MessageVO.getFromId());
        message.setToId(MessageVO.getToId());
        message.setCreateTime(DateTimeUtil.strToDate(MessageVO.getCreateTime()));
        message.setUpdateTime(DateTimeUtil.strToDate(MessageVO.getUpdateTime()));
        return iMessageService.updateMessage(message);
    }
}
