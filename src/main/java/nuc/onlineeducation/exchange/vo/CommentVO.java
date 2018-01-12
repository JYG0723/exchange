package nuc.onlineeducation.exchange.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 14:42 2018/1/11.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {

    private Integer id;

    private Integer userId;

    private Integer entityId;

    private Integer entityType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String content;

    private String username;

    private String headUrl;

    private String introduce;
}
