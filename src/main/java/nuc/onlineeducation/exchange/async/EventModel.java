package nuc.onlineeducation.exchange.async;

import com.google.common.collect.Maps;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Ji YongGuang.
 * @date 16:30 2018/1/15.
 * 具体的事件模型
 */
@NoArgsConstructor
public class EventModel {

    // 类型
    private EventType eventType;
    // 触发者
    private Integer actorId;
    // 事件触发的实体的id
    private Integer entityId;
    // 事件触发的实体的type
    private Integer entityType;
    // 时间被触发的人
    private Integer entityOwnerId;
    // 扩展字段，用来存储不同事件需要的额外信息
    private Map<String, String> exts = Maps.newHashMap();

    public EventModel(EventType eventType) {
        this.eventType = eventType;
    }

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public String getExt(String key) {
        return exts.get(key);
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public Integer getActorId() {
        return actorId;
    }

    public EventModel setActorId(Integer actorId) {
        this.actorId = actorId;
        return this;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(Integer entityId) {
        this.entityId = entityId;
        return this;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(Integer entityType) {
        this.entityType = entityType;
        return this;
    }

    public Integer getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(Integer entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }
}
