package nuc.onlineeducation.exchange.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ji YongGuang.
 * @date 19:43 2018/1/11.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageVO {

    private Integer id;

    private Integer fromId;

    private Integer toId;

    private String content;

    private Integer hasRead;

    // 未读数
    private Integer unReadCount;

    private String conversationId;

    private String createTime;

    private String updateTime;

    private Integer userId;

    private String username;

    private String headUrl;
}
