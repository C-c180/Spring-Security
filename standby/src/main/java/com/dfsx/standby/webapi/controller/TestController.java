package com.dfsx.standby.webapi.controller;

import com.dfsx.standby.webapi.framework.BaseContoller;
import com.dfsx.standby.webapi.model.UserModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by YC on 2019/9/29.
 */
@RestController
public class TestController extends BaseContoller {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(UserModel userModel) throws Exception {
        UserModel userDetails = getUserDetails();
        System.out.println(userDetails);
        return "你好";
    }
}
