package nuc.onlineeducation.exchange.async;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import nuc.onlineeducation.exchange.service.IJedisAdaoterService;
import nuc.onlineeducation.exchange.util.RedisKeyUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Ji YongGuang.
 * @date 19:03 2018/1/15.
 * 时间队列处理类
 */
@Log4j2
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {// 根据事件分发，找处理类

    @Autowired
    private IJedisAdaoterService iJedisAdaoterService;

    // 各事件类型对应的处理类的集合
    private Map<EventType, List<EventHandler>> eventHandlerSupportList = Maps.newHashMap();

    // Spring上下文拿EventHandler的实现类
    private ApplicationContext applicationContext;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterPropertiesSet() throws Exception {
        // 拿到所有Handler
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        // 遍历所有handler，并把每个handler支持的事件 为其添加上该handler的支持
        for (Map.Entry<String, EventHandler> entry : beans.entrySet()
                ) {
            List<EventType> eventTypeList = entry.getValue().getSupportEventTypes();
            for (EventType eventType : eventTypeList
                    ) {
                // 如果已经存在，那么添加该handler支持
                if (!eventHandlerSupportList.containsKey(eventType)) {
                    eventHandlerSupportList.put(eventType, Lists.newArrayList());
                }
                eventHandlerSupportList.get(eventType).add(entry.getValue());
            }
        }

        @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
        Thread thread = new Thread(() -> {
            while (true) {
                if (beans != null) {
                    for (Map.Entry<String, EventHandler> entry : beans.entrySet()
                            ) {
                        String eventKey = RedisKeyUtil.getEventQueueKey();
                        List<String> events = iJedisAdaoterService.brpop(0, eventKey);
                        if (events.contains(eventKey)) {
                            continue;
                        }
                        for (String eventItem :
                                events) {
                            try {
                                EventModel eventModel = objectMapper.readValue(eventItem, EventModel.class);
                                if (!eventHandlerSupportList.containsKey(eventModel.getEventType())) {
                                    log.error("非队列支持的事件");
                                    continue;
                                }
//                                List
                            } catch (IOException e) {
                                log.error("反序列化失败");
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
