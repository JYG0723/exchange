package nuc.onlineeducation.exchange.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Ji YongGuang.
 * @date 8:56 2018/1/8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
