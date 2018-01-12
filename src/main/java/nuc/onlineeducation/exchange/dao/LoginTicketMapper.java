package nuc.onlineeducation.exchange.dao;

import nuc.onlineeducation.exchange.model.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginTicketMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(LoginTicket record);

    int insertSelective(LoginTicket record);

    LoginTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginTicket record);

    int updateByPrimaryKey(LoginTicket record);

    LoginTicket selectByTicket(String ticket);

    int updateStatusByTicketAndStatus(@Param("ticket") String ticket, @Param("status") Integer status);

    LoginTicket selectByUserId(Integer userId);
}