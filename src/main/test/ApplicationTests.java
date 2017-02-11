import com.alibaba.fastjson.JSON;
import com.cjam.springboot.appEntity.CourseLog;
import com.cjam.springboot.mapper.CourseLogDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjam.springboot.Application;
import com.cjam.springboot.appEntity.User;
import com.cjam.springboot.mapper.UserDAO;

/**
 * Created by jam on 2017/2/8.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {

    @Autowired
    private UserDAO userMapper;

    @Autowired
    private CourseLogDAO courseLogDAO;


    @Test
    @Rollback
    public void findByName() throws Exception {
        User u = userMapper.findByName("程国江");
        System.out.println(JSON.toJSON(u));
    }


    @Test
    @Rollback
    public void insertCourseLogDAO() throws Exception {
        CourseLog courseLog = new CourseLog();
        courseLog.setClassName("小二班");
        int insert = courseLogDAO.insert(courseLog);
        System.out.println("insert:"+insert);
    }

}
