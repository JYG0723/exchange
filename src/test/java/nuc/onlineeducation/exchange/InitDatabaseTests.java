package nuc.onlineeducation.exchange;

import nuc.onlineeducation.exchange.dao.QuestionMapper;
import nuc.onlineeducation.exchange.dao.UserMapper;
import nuc.onlineeducation.exchange.model.Question;
import nuc.onlineeducation.exchange.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

/**
 * @author Ji YongGuang.
 * @date 15:08 2018/1/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitDatabaseTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

//    @Rollback
    @Test
    public void initDatabase() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setUsername(String.format("USER%d", i));
            user.setPassword("");
            int gender = i % 2 == 0 ? 0 : 1;
            user.setGender(gender);
            user.setRole(1);
            userMapper.insert(user);
            //添加到数据库后，往后再操作数据时自增字段就已经改变

            user.setPassword("newpassword");
            userMapper.updateByPrimaryKeySelective(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            question.setCreateTime(date);
            question.setUserId(i + 1);//
            question.setTitle(String.format("TITLE{%d}", i));
            question.setContent(String.format("Balaababalalalal Content %d", i));
            questionMapper.insert(question);
        }

//        Assert.assertEquals("newpassword", userMapper.selectByPrimaryKey(1).getPassword());
//        userMapper.deleteByPrimaryKey(1);//删除了id是1的用户
//        Assert.assertNull(userMapper.selectByPrimaryKey(1));
    }
}
