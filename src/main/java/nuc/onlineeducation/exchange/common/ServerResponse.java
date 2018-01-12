package nuc.onlineeducation.exchange.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;


/**
 * @author Ji YongGuang.
 * @date 19:30 2018/1/7.
 * 服务端响应对象
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {// 状态判断
        return this.status == ResponseCodeEnum.SUCCESS.getCode();
    }

    // 成功 - code
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCodeEnum.SUCCESS.getCode());
    }

    // 成功 - code，data
    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCodeEnum.SUCCESS.getCode(), data);
    }

    // 成功 - msg
    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCodeEnum.SUCCESS.getCode(), msg);
    }

    // 成功 - msg，data
    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCodeEnum.SUCCESS.getCode(), msg, data);
    }

    // 公共错误，比如404 不用描述，直接跳转404页面
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCodeEnum.ERROR.getCode(), ResponseCodeEnum.ERROR.getDesc());
    }

    // 需要提示，比如注册用户，提示用户已经存在
    public static <T> ServerResponse<T> createByErrorMessage(String errorDesc) {
        return new ServerResponse<T>(ResponseCodeEnum.ERROR.getCode(), errorDesc);
    }

    // 针对ResponseCode类中各种封装出的错误提供的接口
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorDesc) {
        return new ServerResponse<T>(errorCode, errorDesc);
    }

}
