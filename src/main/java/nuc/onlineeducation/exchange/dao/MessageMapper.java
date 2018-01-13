package nuc.onlineeducation.exchange.dao;

import nuc.onlineeducation.exchange.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> getConversationDetail(@Param("conversationId") String conversationId);// 加分页0

    List<Message> getConversationList(@Param("userId") int userId);

    int getConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    int updateHasRead(@Param("messageId") Integer messageId);

    List<Message> selectMessages();
}