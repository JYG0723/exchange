package nuc.onlineeducation.exchange.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ji YongGuang.
 * @date 8:56 2018/1/8.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionVO {

    private Integer id; // question id

    private Integer userId; // 问题所属用户
    private String username;
    private String introduce;
    private String userHeadUrl;

    private String title;
    private Integer commentCount;
    private String content;
    private String createTime; // 利用jodatime转换
    private String updateTime;
}
