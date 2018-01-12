package nuc.onlineeducation.exchange.model;

import org.springframework.stereotype.Component;

/**
 * @author Ji YongGuang.
 * @date 12:23 2018/1/9.
 */
@Component
public class HostHolder {

    private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>(); // Map<ThreadId,User>

    public User getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public void setUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public void clear() {
        USER_THREAD_LOCAL.remove();
    }
}
