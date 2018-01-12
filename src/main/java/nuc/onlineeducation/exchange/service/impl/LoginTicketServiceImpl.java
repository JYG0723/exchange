package nuc.onlineeducation.exchange.service.impl;

import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.dao.LoginTicketMapper;
import nuc.onlineeducation.exchange.model.LoginTicket;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.ILoginTicketService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Ji YongGuang.
 * @date 1:41 2018/1/9.
 */
@Service(value = "iLoginTicketService")
public class LoginTicketServiceImpl implements ILoginTicketService {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Override
    public String addLoginTicket(User user) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setStatus(Const.TicketStatus.LOG_IN);
        DateTime now = new DateTime();
        DateTime then = now.plusDays(7);// ticket时效7天
        loginTicket.setExpired(then.toDate());
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));// 替换"-"

        loginTicketMapper.insert(loginTicket);
        return loginTicket.getTicket();
    }

    @Override
    public LoginTicket getLoginTicketByUserId(Integer userId) {
        if (StringUtils.isBlank(userId.toString())) {
            return null;
        }
        return loginTicketMapper.selectByUserId(userId);
    }

    @Override
    public LoginTicket getLoginTicketByTicket(String ticket) {
        if (StringUtils.isBlank(ticket)) {
            return null;
        }
        return loginTicketMapper.selectByTicket(ticket);
    }

    @Override
    public Integer updateStatusByTicketAndStatus(String ticket, Integer status) {
        if (StringUtils.isBlank(ticket)) {
            return null;
        }
        return loginTicketMapper.updateStatusByTicketAndStatus(ticket, status);
    }
}
