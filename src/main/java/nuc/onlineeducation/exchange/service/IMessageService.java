package nuc.onlineeducation.exchange.service;

import com.github.pagehelper.PageInfo;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.Message;
import nuc.onlineeducation.exchange.vo.MessageVO;

/**
 * @author Ji YongGuang.
 * @date 19:25 2018/1/11.
 */
public interface IMessageService {

    ServerResponse<Integer> saveMessage(Message message);

    ServerResponse<PageInfo> getConversationDetail(String conversationId, Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> getConversationWithTouristsList(Integer userId, Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> getConversationWithAdminList(Integer userId, Integer pageNum, Integer pageSize);

    ServerResponse<Integer> getConversationUnreadCount(Integer userId, String conversationId);

    ServerResponse removeMessageById(Integer messageId);

    ServerResponse<PageInfo> getMessages(Integer pageNum, Integer pageSize);

    ServerResponse updateMessage(Message message);

    ServerResponse<MessageVO> getMessageById(Integer messageId);
}
