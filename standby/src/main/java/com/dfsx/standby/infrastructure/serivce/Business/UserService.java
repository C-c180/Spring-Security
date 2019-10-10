package com.dfsx.standby.infrastructure.serivce.Business;

import com.dfsx.standby.webapi.dto.UserParam;
import com.dfsx.standby.webapi.model.UserModel;

/**
 * Created by YC on 2019/9/29.
 */
public interface UserService {
    UserModel register(UserParam userParam) throws Exception;

    String login(String username, String password) throws Exception;

    UserModel getUserByUsername(String username);

    String refreshToken(String oldToken);
}
