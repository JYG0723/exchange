package nuc.onlineeducation.exchange.config;

import nuc.onlineeducation.exchange.interceptor.LoginRequiredInterceptor;
import nuc.onlineeducation.exchange.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Ji YongGuang.
 * @date 13:59 2018/1/9.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInteceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注意顺序
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInteceptor)
                .addPathPatterns("/users").excludePathPatterns("/users/people/{id}/activities").excludePathPatterns
                ("/users/register").excludePathPatterns("/users/login").excludePathPatterns("/users/existence/check").excludePathPatterns("/users/logout").excludePathPatterns("/users/legality/check")
                .addPathPatterns("/users/password/reset")
                .addPathPatterns("/questions/add")
                .addPathPatterns("/comments/add")
                .addPathPatterns("/messages/");
        super.addInterceptors(registry);
    }
}
