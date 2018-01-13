package nuc.onlineeducation.exchange.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 14:48 2018/1/7.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question {

    private Integer id;

    private String title;

    private Integer userId;

    private Integer commentCount;

    private Date createTime;

    private Date updateTime;

    private String content;
}