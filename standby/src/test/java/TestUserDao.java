import com.dfsx.standby.GlobalConfig;
import com.dfsx.standby.infrastructure.repository.dao.UserDao;
import com.dfsx.standby.webapi.model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by YC on 2019/9/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GlobalConfig.class)
public class TestUserDao {

    @Autowired
    private UserDao userDao;

    @Rollback(value = false)
    @Test
    public void testUser() {
        UserModel userModel=new UserModel();
        userModel.setUsername("张三");
//        userDao.insert(userModel);
    }
}
