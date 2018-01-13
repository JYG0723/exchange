package nuc.onlineeducation.exchange.controller.backend;

import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Ji YongGuang.
 * @date 0:12 2018/1/13.
 */
@RestController
@RequestMapping(value = "/manage/users")
public class UserManagerController {

    @Autowired
    private IUserService iUserService;

    /**
     * 管理员登录
     *
     * @param username    用户名
     * @param password    密码
     * @param httpSession httpSession
     * @return
     */
    @PostMapping(value = "login.do")
    public ServerResponse<User> login(String username, String password, HttpSession httpSession) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.UserRoleEnum.ROLE_ADMIN.getCode()) {
                httpSession.setAttribute(Const.CURRENT_USER, user);
                return response;
            } else {
                return ServerResponse.createByErrorMessage("不是管理员无法登陆");
            }
        }
        return response;
    }
}
