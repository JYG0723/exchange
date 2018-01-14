package nuc.onlineeducation.exchange.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 22:48 2018/1/13.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {

    private Integer id;

    private String title;

    private Integer userId;

    private Integer origin;

    private Date createTime;

    private Date updateTime;

    private String detail;
}