package nuc.onlineeducation.exchange.dao;

import nuc.onlineeducation.exchange.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    List<Question> selectListByUserId(@Param("userId") Integer userId);

    int updateCommentCount(@Param("entityId") Integer entityId, @Param("count") Integer count);

    List<Question> selectList();
}