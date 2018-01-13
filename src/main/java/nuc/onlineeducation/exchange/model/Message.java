package nuc.onlineeducation.exchange.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 0:31 2018/1/9.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    private Integer id;

    private Integer fromId;

    private Integer toId;

    private Integer hasRead;

    private String conversationId;

    private Date createTime;

    private Date updateTime;

    private String content;

    public String getConversationId() {
        if (fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        } else {
            return String.format("%d_%d", toId, fromId);
        }
    }
}