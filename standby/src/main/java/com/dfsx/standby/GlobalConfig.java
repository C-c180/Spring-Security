package com.dfsx.standby;

import com.dfsx.standby.infrastructure.serivce.Business.UserService;
import com.dfsx.standby.webapi.bo.AdminUserDetails;
import com.dfsx.standby.webapi.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan({ "com.dfsx.standby.infrastructure" })
@ImportResource("classpath:global-context.xml")
public class GlobalConfig {
    @Autowired
    private UserService _userService;

    @Bean
    public PasswordEncoder myEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserModel user = _userService.getUserByUsername(username);
            if (user != null) {
                return new AdminUserDetails(user);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }
}
