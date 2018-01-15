package nuc.onlineeducation.exchange.async;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 18:45 2018/1/15.
 */
public interface EventHandler {// 映射类，寻找事件对应处理的handler

    /**
     * 处理时间
     * @param eventModel 具体的事件
     */
    void doHandler(EventModel eventModel);

    /**
     * 获取handler支持的事件
     * @return
     */
    List<EventType> getSupportEventTypes();
}
