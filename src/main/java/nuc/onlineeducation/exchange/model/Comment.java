package nuc.onlineeducation.exchange.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 0:32 2018/1/9.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {

    private Integer id;

    private Integer userId;

    private Integer entityId;

    private Integer entityType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String content;
}