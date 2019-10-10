package com.dfsx.standby.webapi.framework;

import com.dfsx.standby.webapi.bo.AdminUserDetails;
import com.dfsx.standby.webapi.model.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by YC on 2019/9/29.
 */
public class BaseContoller {
    public UserModel getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdminUserDetails principal = (AdminUserDetails) authentication.getPrincipal();
        return principal.getUserModel();
    }
}
