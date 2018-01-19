package nuc.onlineeducation.exchange.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ji YongGuang.
 * @date 23:53 2018/1/7.
 * 常量管理类
 */
public final class Const {

    private Const() {
    }

    public static final Integer ADMIN_ID = 0; // 管理员id

    public static final String INBOUND_LINKS = "localhost:8080";//站内链接

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PHONE = "phone";

    public interface Gender {// 用户性别
        int MALE = 0; // 男
        int FEMALE = 1; // 女
    }

    @AllArgsConstructor
    @Getter
    public enum UserRoleEnum {    // 角色的权限

        ROLE_ADMIN(0, "管理员"),
        ROLE_STUDENT(1, "学生"),
        ROLE_TEACHER(2, "老师");

        private int code;
        private String value;
    }

    public interface TicketStatus {// T票的状态
        int LOG_IN = 0;
        int LOG_OUT = 1;
    }

    public interface CommentStatus {// 评论的状态
        int COMMENT_VISIBLE = 0; // 评论不可见
        int COMMENT_INVISIBLE = 1; // 评论可见
    }

    @AllArgsConstructor
    @Getter
    public enum CommentEntityTypeEnum {// 评论的实体的类型
        QUESTION(0, "问题"),
        COMMENT(1, "评论");

        private int code;
        private String value;
    }

    @AllArgsConstructor
    @Getter
    public enum LikeEntityTypeEnum {// 点赞的实体类型
        COMMENT(1, "评论");

        private int code;
        private String value;
    }

    public interface MessageStatus {// 站内信的状态
        int HAS_READ = 0;// 已读
        int UN_READ = 1;// 未读
    }

    @AllArgsConstructor
    @Getter
    public enum OriginTypeEnum {// 课题来源
        SOFTWARE_COLLEGE(14, "软件学院"),
        BIGDATA_COLLEGE(7, "大数据学院"),
        CHEMICALINDUSTRY_COLLEGE(1, "化工院"),
        UNKNOWN(100, "未知来源");

        private int code;
        private String value;
    }

    @AllArgsConstructor
    @Getter
    public enum UploadFileType {// 上传文件类型
        UPLOAD_PATH_IMAGE(1, "img"),
        UPLOAD_PATH_FILE(2, "file");

        private int code;
        private String value;
    }

}