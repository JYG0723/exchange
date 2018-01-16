package nuc.onlineeducation.exchange.async.handler;

import nuc.onlineeducation.exchange.async.EventHandler;
import nuc.onlineeducation.exchange.async.EventModel;
import nuc.onlineeducation.exchange.async.EventType;
import nuc.onlineeducation.exchange.common.Const;
import nuc.onlineeducation.exchange.model.Message;
import nuc.onlineeducation.exchange.service.IMessageService;
import nuc.onlineeducation.exchange.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ji YongGuang.
 * @date 21:34 2018/1/15.
 */
@Service
public class LikeHandler implements EventHandler {

    private static final String PRAISE_PREFIX = "http://127.0.0.1:8080/questions/";

    @Autowired
    private IMessageService iMessageService;

    @Autowired
    private IUserService iUserService;

    /**
     * Handler具体的业务
     *
     * @param eventModel 具体的事件
     */
    @Override
    public void doHandler(EventModel eventModel) {
        Message message = new Message();
        // 管理员负责业务通知
        message.setFromId(Const.ADMIN_ID);
        message.setToId(eventModel.getEntityOwnerId());
        // 直接让用户跳到问题详情页
        message.setContent("用户:" + iUserService.getUserById(eventModel.getActorId()).getData().getUsername() +
                "赞了你的评论，" + PRAISE_PREFIX + eventModel.getExt("questionId"));
        message.setHasRead(Const.CommentStatus.COMMENT_INVISIBLE);
        iMessageService.saveMessage(message);
    }

    /**
     * Handler受理的事件
     *
     * @return
     */
    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
