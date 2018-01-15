package nuc.onlineeducation.exchange.service.impl;

import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ResponseCodeEnum;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.common.TokenCache;
import nuc.onlineeducation.exchange.dao.UserMapper;
import nuc.onlineeducation.exchange.model.LoginTicket;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.IUserService;
import nuc.onlineeducation.exchange.util.MD5Util;
import nuc.onlineeducation.exchange.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author Ji YongGuang.
 * @date 0:07 2018/1/8.
 */
@Service(value = "iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketServiceImpl loginTicketService;

    @Override
    public ServerResponse<User> register(User user) {
        int result = userMapper.checkUsername(user.getUsername());
        if (result > 0) {
            return ServerResponse.createByErrorMessage("用户名已经存在");
        }
        result = userMapper.checkEmail(user.getEmail());
        if (result > 0) {
            return ServerResponse.createByErrorMessage("邮箱已经注册");
        }
        user.setRole(Const.UserRoleEnum.ROLE_STUDENT.getCode());// 默认学生
        // MD5
        String md5Password = MD5Util.MD5EncodeUtf8(user.getPassword() + PropertiesUtil.getProperty("password.salt"));
        user.setPassword(md5Password);

        Random random = new Random();
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));

        int gender = random.nextInt(100) % 2 == 0 ? 0 : 1;
        user.setGender(gender);

        result = userMapper.insert(user);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        user = this.getUserByUsername(user.getUsername()).getData();
        // 生成t票
        loginTicketService.addLoginTicket(user);// 注册后保证用户直接登录
        return ServerResponse.createBySuccess("注册成功", user);
    }

    @Override
    public ServerResponse<User> login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(),
                    "用户名或密码不能为空");
        }

        int result = userMapper.checkUsername(username);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("该用户不存在");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password + PropertiesUtil.getProperty("password.salt"));
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        // 判断t票
        LoginTicket loginTicket = loginTicketService.getLoginTicketByUserId(user.getId());
        if (loginTicket == null || loginTicket.getStatus() == Const.TicketStatus.LOG_OUT) {
            // 生成T票
            loginTicketService.addLoginTicket(user);
            // 密码干掉
            user.setPassword(StringUtils.EMPTY);
        }
        return ServerResponse.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponse logout(String ticket) {
        if (StringUtils.isBlank(ticket)) {// 如果没T票直接退出
            return ServerResponse.createByErrorMessage("用户尚未登录");
        }
        // 失效
        Integer result = loginTicketService.updateStatusByTicketAndStatus(ticket, Const.TicketStatus.LOG_OUT);
        if (result > 0) {
            return ServerResponse.createBySuccessMessage("用户成功退出登录");
        }
        return ServerResponse.createByErrorMessage("用户退出登录失败");
    }

    @Override
    public ServerResponse<User> getUserById(Integer userId) {
        if (StringUtils.isBlank(userId.toString())) {
            return null;
        }
        User user = userMapper.selectByPrimaryKey(userId);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(str)) {
            if (type.equals(Const.USERNAME)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已经存在");
                }
            }
            if (type.equals(Const.EMAIL)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("email已经存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数不能为空");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> getQuestionByUsername(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        } else {
            return ServerResponse.createByErrorMessage("该用户找回密码的问题是空的");
        }
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(question) || StringUtils.isBlank(answer)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.ILLEGAL_ARGUEMENT.getCode(),
                    ResponseCodeEnum
                            .ILLEGAL_ARGUEMENT.getDesc());
        }
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            // 标识唯一用户
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.FORGET_PASSWORD_TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("用户的答案错误");
    }

    @Override
    public ServerResponse forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误,token需要传递");
        }
        ServerResponse<String> validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        if (StringUtils.isBlank(passwordNew)) {
            return ServerResponse.createByErrorMessage("密码不能为空");
        }

        String token = TokenCache.getKey(TokenCache.FORGET_PASSWORD_TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token无效,或者过期");
        }
        if (StringUtils.equals(token, forgetToken)) { // expireAfterAccess方法设置的12小时过期
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew + PropertiesUtil.getProperty("password.salt"));
            int rowCount = userMapper.updatePassword(username, md5Password);

            if (rowCount > 0) {
                TokenCache.removeKey(TokenCache.FORGET_PASSWORD_TOKEN_PREFIX + username);
                return ServerResponse.createBySuccessMessage("密码修改成功");
            }
        } else {
            return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("密码修改失败");
    }

    @Override
    public ServerResponse resetPassword(String passwordOld, String passwordNew, User user) {
        if (StringUtils.isBlank(passwordOld)) {
            return ServerResponse.createByErrorMessage("参数错误,token需要传递");
        }
        ServerResponse<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        if (StringUtils.isBlank(passwordNew)) {
            return ServerResponse.createByErrorMessage("密码不能为空");
        }

        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(passwordNew + PropertiesUtil.getProperty("password.salt"));
        user.setPassword(md5Password);

        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("密码修改成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        // email 不能是已被注册的
        int resultCount = userMapper.checkEmail(user.getEmail());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("email已经存在，请更换email再尝试更新");
        }

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUsername(user.getUsername());
        updateUser.setHeadUrl(user.getHeadUrl());// 换头像
        updateUser.setGender(user.getGender());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setProfile(user.getProfile());
        updateUser.setIntroduce(user.getIntroduce());
        updateUser.setPosition(user.getPosition());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCpunt = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCpunt > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return ServerResponse.createByErrorMessage("用户名不能为空");
        }
        User user = userMapper.selectByUsername(username);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("该用户不存在");
    }

    @Override
    public ServerResponse<List<User>> getTeachers() {
        List<User> teachers = userMapper.selectTeachers(Const.UserRoleEnum.ROLE_TEACHER.getCode());
        return ServerResponse.createBySuccess("查询老师列表成功", teachers);
    }

    @Override
    public ServerResponse checkAdminRole(User user) {
        // 这里不做非空判断如果user传过来为空 会报空指针
        if (user != null && user.getRole().intValue() == Const.UserRoleEnum.ROLE_ADMIN.getCode()) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse checkTeacherRole(User user) {
        // 这里不做非空判断如果user传过来为空 会报空指针
        if (user != null && user.getRole().intValue() == Const.UserRoleEnum.ROLE_TEACHER.getCode()) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User currentUser = userMapper.selectByPrimaryKey(userId);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("未找到当前用户");
        }
        // 密码干掉
        currentUser.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("成功查询当前用户的个人信息",currentUser);
    }

    public static void main(String[] args) {
        String md5Password = MD5Util.MD5EncodeUtf8("laoshi" + PropertiesUtil.getProperty("password.salt"));
        System.out.println(md5Password);
    }

}
