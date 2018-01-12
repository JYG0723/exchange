package nuc.onlineeducation.exchange.interceptor;

import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.model.HostHolder;
import nuc.onlineeducation.exchange.model.LoginTicket;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.ILoginTicketService;
import nuc.onlineeducation.exchange.service.IUserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ji YongGuang.
 * @date 11:38 2018/1/9.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private ILoginTicketService iLoginTicketService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
            o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            Cookie[] cookies = httpServletRequest.getCookies();
            for (Cookie cookieItem : cookies) {// 找T票
                if (cookieItem.getName().equals("ticket")) {
                    ticket = cookieItem.getValue();
                    break;
                }
            }
        }

        if (ticket != null) {
            LoginTicket loginTicket = iLoginTicketService.getLoginTicketByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new DateTime().toDate())
                    || loginTicket.getStatus().intValue() == Const.TicketStatus.LOG_OUT) {// T票无效
                return true;// 放行去生成T票
            }
            //  T票有效
            User user = iUserService.getUserById(loginTicket.getId()).getData();
            if (user != null) {
                hostHolder.setUser(user);// 同一Thread重置对象
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {// 所有页面注入user -> Test能不能访问到
            modelAndView.addObject(hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
