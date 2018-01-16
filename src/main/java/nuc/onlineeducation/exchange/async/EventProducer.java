package nuc.onlineeducation.exchange.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import nuc.onlineeducation.exchange.service.IJedisAdaoterService;
import nuc.onlineeducation.exchange.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ji YongGuang.
 * @date 17:04 2018/1/15.
 */
@Log4j2
@Service
public class EventProducer {

    @Autowired
    private IJedisAdaoterService iJedisAdaoterService;

    /**
     * 向队列中推送事件
     * @param eventModel 具体的事件
     * @return
     */
    public boolean fireEvent(EventModel eventModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonEvent = objectMapper.writeValueAsString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            iJedisAdaoterService.lpush(key, jsonEvent);
            return true;
        } catch (JsonProcessingException e) {
            log.error("推送事件到队列失败");
            return false;
        }
    }
}
