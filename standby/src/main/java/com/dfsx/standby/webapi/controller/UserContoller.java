package com.dfsx.standby.webapi.controller;

import com.dfsx.standby.infrastructure.serivce.Business.UserService;
import com.dfsx.standby.infrastructure.utils.JWTUtil;
import com.dfsx.standby.webapi.common.CommonResult;
import com.dfsx.standby.webapi.dto.UserParam;
import com.dfsx.standby.webapi.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YC on 2019/9/29.
 */
@Api(tags = "用户相关接口文档", description = "对用户登陆及注册等接口")
@RestController
public class UserContoller {


    @Autowired
    private UserService _userService;

    @ApiOperation("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<UserModel> register(@Validated @RequestBody UserParam userParam) throws Exception {
        UserModel user = _userService.register(userParam);
        if (user == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(user);
    }

    @ApiOperation("登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@Validated @RequestBody UserParam userParam) throws Exception {
        String token = _userService.login(userParam.getUsername(), userParam.getPassword());
        if (token == null) {
            return CommonResult.unauthoried("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", JWTUtil.TOKENHEAD);
        return CommonResult.success(token);
    }

    @ApiOperation("刷新token")
    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(JWTUtil.TOKENHEADER);
        String newToken = _userService.refreshToken(token);
        if (newToken == null) {
            return CommonResult.failed();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", newToken);
        tokenMap.put("tokenHead", JWTUtil.TOKENHEAD);
        return CommonResult.success(tokenMap);
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public void loginOut() {

    }
}
