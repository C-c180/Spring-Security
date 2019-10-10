package test;

import com.dfsx.standby.GlobalConfig;
import com.dfsx.standby.infrastructure.serivce.Business.UserService;
import com.dfsx.standby.webapi.dto.UserParam;
import com.dfsx.standby.webapi.model.UserModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by YC on 2019/10/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GlobalConfig.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @Rollback(value = false)
    public void register() throws Exception {
        UserParam userParam = new UserParam();
        userParam.setUsername("admin");
        userParam.setPassword("123456");
        userService.register(userParam);
    }

    @Test
    public void login() throws Exception {

    }

    @Test
    public void getUserByUsername() throws Exception {
        UserModel admin = userService.getUserByUsername("admin");
        Assert.assertNotNull(admin);
    }

    @Test
    public void refreshToken() throws Exception {

    }

}