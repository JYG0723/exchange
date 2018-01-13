package nuc.onlineeducation.exchange.dao;

import nuc.onlineeducation.exchange.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    int checkUsername(String username);

    int checkEmail(String email);

    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String
            answer);

    int updatePassword(@Param("username") String username, @Param("password") String password);

    int checkPassword(@Param("password") String password, @Param("userId") Integer userId);

    User selectByUsername(@Param("username") String username);

    List<User> selectTeachers(@Param("teacherRole") Integer teacherRoel);

}