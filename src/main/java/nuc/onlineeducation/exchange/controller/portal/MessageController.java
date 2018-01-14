package nuc.onlineeducation.exchange.controller.portal;

import com.github.pagehelper.PageInfo;
import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.HostHolder;
import nuc.onlineeducation.exchange.model.Message;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.IMessageService;
import nuc.onlineeducation.exchange.service.IUserService;
import nuc.onlineeducation.exchange.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ji YongGuang.
 * @date 1:06 2018/1/10.
 */
@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    IMessageService iMessageService;

    @Autowired
    IUserService iUserService;

    /**
     * 获取会话列表
     *
     * @param pageNum 页数
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/")
    public ServerResponse<PageInfo> getConversations(@RequestParam(value = "pageNum", defaultValue = "0") Integer
                                                                pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer
                                                                pageSize) {
        int localUserId = hostHolder.getUser().getId();
        return iMessageService.getConversationList(localUserId, pageNum, pageSize);
    }

    /**
     * 获取会话详情
     *
     * @param conversationId 会话id
     * @param pageNum        页数
     * @param pageSize       页面大小
     * @return
     */
    @GetMapping("/{id}")
    public ServerResponse<PageInfo> getConversationDetail(@PathVariable(value = "id") String conversationId,
                                                          @RequestParam(value = "pageNum", defaultValue = "0")
                                                                  Integer pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer
                                                                  pageSize) {
        return iMessageService.getConversationDetail(conversationId, pageNum, pageSize);
    }

    /**
     * 新增消息
     *
     * @param toName  接收方
     * @param content 上下文
     * @return
     */
    @PostMapping("/")
    public ServerResponse messageSave(@RequestParam("toName") String toName,
                                      @RequestParam("content") String content) {

        User user = iUserService.getUserByUsername(toName).getData();
        if (user == null) {
            return ServerResponse.createByErrorMessage("该用户不存在");
        }

        Message message = new Message();
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(user.getId());
        message.setContent(PropertiesUtil.getProperty("."));
        message.setHasRead(Const.MessageStatus.UN_READ);
        return iMessageService.saveMessage(message);
    }
}
