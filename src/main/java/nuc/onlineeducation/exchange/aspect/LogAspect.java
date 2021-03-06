package nuc.onlineeducation.exchange.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Ji YongGuang.
 * @date 17:49 2018/1/6.
 */
@Aspect
@Log4j2
@Component
public class LogAspect {

    private static Long executeLong = null;

    @Pointcut("execution(public * nuc.onlineeducation.exchange.controller.*Controller.*(..))")
    public void controllerLog() {

    }

    @Before(value = "controllerLog()")
    public void beforMethod(JoinPoint joinPoint) {
        executeLong = System.currentTimeMillis();

        Signature targetClass = joinPoint.getSignature();
        log.info("目标方法所属类的简单类名:{} - 目标方法名为:{}", targetClass.getDeclaringType().getSimpleName(), targetClass.getName());

        StringBuffer argsBuffer = new StringBuffer();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            argsBuffer.append(String.format("目标方法的第%d个参数是:", (i + 1)) + args[i].toString() + " ");
        }
        log.info(argsBuffer.toString());
    }

    @After(value = "controllerLog()")
    public void afterMethod() {
        executeLong = System.currentTimeMillis() - executeLong;
        log.info("目标方法执行时间:{}",executeLong + "毫秒");
    }
}
