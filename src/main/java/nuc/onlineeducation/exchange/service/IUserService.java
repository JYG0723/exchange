package nuc.onlineeducation.exchange.service;

import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.User;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 0:07 2018/1/8.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<User> register(User user);

    ServerResponse<User> getUserById(Integer userId);

    ServerResponse logout(String ticket);

    ServerResponse checkValid(String str, String type);

    ServerResponse<String> getQuestionByUsername(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getUserByUsername(String username);

    ServerResponse<List<User>> getTeachers();
}
