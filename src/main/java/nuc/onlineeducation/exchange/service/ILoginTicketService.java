package nuc.onlineeducation.exchange.service;

import nuc.onlineeducation.exchange.model.LoginTicket;
import nuc.onlineeducation.exchange.model.User;

/**
 * @author Ji YongGuang.
 * @date 1:41 2018/1/9.
 */
public interface ILoginTicketService {

    String addLoginTicket(User user);

    LoginTicket getLoginTicketByUserId(Integer userId);

    LoginTicket getLoginTicketByTicket(String ticket);

    Integer updateStatusByTicketAndStatus(String ticket, Integer status);
}
