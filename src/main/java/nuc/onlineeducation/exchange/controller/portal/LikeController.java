package nuc.onlineeducation.exchange.controller.portal;

import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.common.ResponseCodeEnum;
import nuc.onlineeducation.exchange.common.ServerResponse;
import nuc.onlineeducation.exchange.model.HostHolder;
import nuc.onlineeducation.exchange.model.User;
import nuc.onlineeducation.exchange.service.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ji YongGuang.
 * @date 0:13 2018/1/15.
 */
@RestController
@RequestMapping(value = "/likes/")
public class LikeController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private ILikeService iLikeService;

    @GetMapping("/like/{id}")
    public ServerResponse like(@PathVariable(value = "id") Integer commentId) {
        User user = hostHolder.getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(), ResponseCodeEnum
                    .NEED_LOGIN.getDesc());
        }
        // 先写死喜欢评论
        return iLikeService.like(user.getId(), Const.LikeEntityTypeEnum.COMMENT.getCode(),
                commentId);
    }

    @GetMapping("/dislike/{id}")
    public ServerResponse disLike(@PathVariable(value = "id") Integer commentId) {
        User user = hostHolder.getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(), ResponseCodeEnum
                    .NEED_LOGIN.getDesc());
        }
        return iLikeService.disLike(user.getId(), Const.LikeEntityTypeEnum.COMMENT.getCode(),
                commentId);
    }
}
