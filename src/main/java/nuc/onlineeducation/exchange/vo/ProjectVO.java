package nuc.onlineeducation.exchange.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ji YongGuang.
 * @date 0:09 2018/1/14.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVO {

    private Integer id;

    private String title;

    private Integer userId;

    private Integer origin;

    private String createTime;

    private String updateTime;

    private String detail;

    private String username;

    private String originName;

}
