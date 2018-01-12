package nuc.onlineeducation.exchange.dao;

import nuc.onlineeducation.exchange.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectCommentsByEntity(@Param("entityId") Integer entityId, @Param("entityType") Integer entityType);

    int getCommentCountByEntity(@Param("entityId") Integer entityId, @Param("entityType") Integer entityType);

    int updateCommentStatus(@Param("commentId") Integer commentId, @Param("status") Integer status);
}