package nuc.onlineeducation.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Ji YongGuang.
 * @date 0:31 2018/1/9.
 */
@Data
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