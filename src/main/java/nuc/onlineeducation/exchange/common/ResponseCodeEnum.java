package nuc.onlineeducation.exchange.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ji YongGuang.
 * @date 23:45 2018/1/7.
 * 响应编码枚举类
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {

    // 成功
    SUCCESS(0, "SUCCESS"),
    // 错误
    ERROR(1, "ERROR"),
    // 参数异常
    ILLEGAL_ARGUEMENT(2, "ILLEGAL_ARGUMENT"),
    // 未登录
    NEED_LOGIN(10, "NEED_LOGIN");

    private final int code;
    private final String desc;
}
