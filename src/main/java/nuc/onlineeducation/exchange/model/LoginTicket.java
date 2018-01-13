package nuc.onlineeducation.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 0:31 2018/1/9.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginTicket {

    private Integer id;

    private Integer userId;

    private String ticket;

    private Date expired;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}