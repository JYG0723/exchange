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
public class User {

    private Integer id;

    private String username;

    private String password;

    private String headUrl;

    private Integer gender;

    private String email;

    private String phone;

    private String position;

    private String introduce;

    private String question;

    private String answer;

    private Integer role;

    private Date createTime;

    private Date updateTime;

    private String profile;
}