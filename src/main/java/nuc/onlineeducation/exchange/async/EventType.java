package nuc.onlineeducation.exchange.async;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ji YongGuang.
 * @date 15:15 2018/1/15.
 * 事件的类型
 */
@Getter
@AllArgsConstructor
public enum EventType {

    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;
}
