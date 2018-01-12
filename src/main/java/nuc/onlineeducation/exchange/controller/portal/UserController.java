package nuc.onlineeducation.exchange.controller.portal;

import com.github.pagehelper.PageInfo;
import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.HostHolder;
import nuc.onlineeducation.exchange.model.LoginTicket;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.ILoginTicketService;
import nuc.onlineeducation.exchange.service.IQuestionService;
import nuc.onlineeducation.exchange.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ji YongGuang.
 * @date 11:54 2018/1/8.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IQuestionService iQuestionService;

    @Autowired
    private ILoginTicketService iLoginTicketService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 用户最近动态
     *
     * @param userId   用户id
     * @param pageNum  页数
     * @param pageSize 页面大小
     * @return 分页后的对象
     */
    @GetMapping(value = "/people/{id}/activities")
    public ServerResponse<PageInfo> index(@PathVariable(value = "id") Integer userId,
                                          @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer
                                                  pageSize) {
        return iQuestionService.getLatestQuestions(userId, pageNum, pageSize);
    }

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @param email
     * @param httpServletResponse
     * @return
     */
    @PostMapping(value = "/register")
    public ServerResponse register(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "password") String password,
                                   @RequestParam(value = "email") String email,
                                   HttpServletResponse httpServletResponse) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        ServerResponse<User> serverResponse = iUserService.register(user);
        if (serverResponse.isSuccess()) {// 注册成功，t票生成
            LoginTicket loginTicket = iLoginTicketService.getLoginTicketByUserId(serverResponse.getData().getId());
            if (loginTicket != null) {// 写入客户端浏览器的cookie中
                Cookie cookie = new Cookie("ticket", loginTicket.toString());
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
            }
        }
        return serverResponse;
    }

    /**
     * 用户登录
     *
     * @param username            用户名
     * @param password            密码
     * @param rememberme          记住我 -> 7天
     * @param next                目标地址
     * @param httpServletResponse httpServletResponse
     * @return
     */
    @PostMapping(value = "/login")
    public ServerResponse login(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "rememberme", defaultValue = "false") Boolean rememberme,
                                @RequestParam(value = "next", required = false) String next,
                                HttpServletResponse httpServletResponse) {
        ServerResponse<User> serverResponse = iUserService.login(username, password);
        if (serverResponse.isSuccess()) {
            LoginTicket loginTicket = iLoginTicketService.getLoginTicketByUserId(serverResponse.getData().getId());
            if (loginTicket != null) {// 写入客户端浏览器的cookie中
                Cookie cookie = new Cookie("ticket", loginTicket.getTicket().toString());
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
                if (StringUtils.isNotBlank(next) && next.contains("localhost:8080")) {// 站内链接
                    return ServerResponse.createBySuccess(next);
                }
            }
            return ServerResponse.createBySuccess("登录成功", Const.INBOUND_LINKS); // home
        }
        return serverResponse; // home
    }

    /**
     * 检查用户是否在线
     *
     * @return
     */
    @GetMapping(value = "/existence/check")
    public ServerResponse checkOnLine(@CookieValue(value = "ticket") String ticket) {
        if (StringUtils.isBlank(ticket)) {
            return ServerResponse.createByErrorMessage("该用户当前不在线");
        }
        LoginTicket loginTicket = iLoginTicketService.getLoginTicketByTicket(ticket);
        if (loginTicket == null) {
            return ServerResponse.createByErrorMessage("该用户当前不在线");
        }
        return ServerResponse.createBySuccessMessage("该用户当前在线");
    }

    /**
     * 退出登录
     *
     * @param ticket T票
     * @return
     */
    @GetMapping(value = "/logout")
    public ServerResponse logout(@CookieValue(name = "ticket") String ticket) {
        return iUserService.logout(ticket);
    }

    /**
     * 注册时校验用户名及邮箱 -> 实时
     *
     * @param str  value
     * @param type username / email
     * @return
     */
    @GetMapping(value = "/legality/check")// 丢.com
    public ServerResponse checkValid(@RequestParam(value = "str") String str,
                                     @RequestParam(value = "type") String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 忘记密码获取用户密码问题
     *
     * @param username 用户名
     * @return
     */
    @GetMapping(value = "/oblivion/questions/{username}")
    public ServerResponse<String> forgetGetQuestion(@PathVariable(value = "username") String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 用户问题答案的验证
     *
     * @param username 用户名
     * @param question 问题
     * @param answer   答案
     * @return String 承载的是token
     */
    @PostMapping(value = "/oblivion/answer/check")
    public ServerResponse<String> forgetCheckAnswer(@RequestParam(value = "username") String username,
                                                    @RequestParam(value = "question") String question,
                                                    @RequestParam(value = "answer") String answer) {
        // token通过guava存到了内存中
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 未登录 -> 根据forgetToken重置密码
     *
     * @param username    用户名
     * @param passwordNew 新密码
     * @param forgetToken forgetToken
     * @return
     */
    @PostMapping(value = "/oblivion/password/reset")
    public ServerResponse<String> forgetResetPassword(@RequestParam(value = "username") String username,
                                                      @RequestParam(value = "passwordNew") String passwordNew,
                                                      @RequestParam(value = "forgetToken") String forgetToken) {
        // 忘记密码 -> 修改密码
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * 登录状态下修改密码
     *
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @return 接口没测 postman访问不到threadlocal中的内容
     */
    @PostMapping(value = "/password/reset")
    public ServerResponse<String> resetPassword(@RequestParam(value = "passwordOld") String passwordOld,
                                                @RequestParam(value = "passwordNew") String passwordNew) {// 接口没测
        User currentUser = hostHolder.getUser();
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("当前用户未登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, currentUser);
    }

    /**
     * 更新用户信息
     *
     * @param id
     * @param user
     * @return
     */
    @PutMapping(value = "/{id}")
    public ServerResponse<User> updateInformation(@PathVariable(value = "id") Integer id, User user) {
        User currentUser = hostHolder.getUser();
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
//        不行就用cookie
//        LoginTicket loginTicket = iLoginTicketService.getLoginTicketByTicket(ticket);
//        User currentUser = iUserService.getUserById(loginTicket.getUserId()).getData();
        user.setId(currentUser.getId());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            hostHolder.setUser(response.getData());
        }
        return response;
    }
}
