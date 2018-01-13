package nuc.onlineeducation.exchange.controller.backend;

import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.LoginTicket;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.ILoginTicketService;
import nuc.onlineeducation.exchange.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Ji YongGuang.
 * @date 0:12 2018/1/13.
 */
@RestController
@RequestMapping(value = "/manage/users")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ILoginTicketService iLoginTicketService;

    /**
     * 管理员登录
     *
     * @param username    用户名
     * @param password    密码
     * @param httpSession httpSession
     * @return
     */
    @PostMapping(value = "/login")
    public ServerResponse<User> login(String username, String password, HttpSession httpSession, HttpServletResponse
            httpServletResponse) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.UserRoleEnum.ROLE_ADMIN.getCode()) {
                LoginTicket loginTicket = iLoginTicketService.getLoginTicketByUserId(response.getData().getId());
                if (loginTicket != null) {// 写入客户端浏览器的cookie中
                    Cookie cookie = new Cookie("ticket", loginTicket.getTicket().toString());
                    cookie.setPath("/");
                    httpServletResponse.addCookie(cookie);
                }
                return response;
            } else {
                return ServerResponse.createByErrorMessage("不是管理员无法登陆");
            }
        }
        return response;
    }

}
