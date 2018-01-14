package nuc.onlineeducation.exchange.vo;

import lombok.*;

import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 18:32 2018/1/11.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDetailVO {

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

    private List<CommentVO> commentVOList;
}
