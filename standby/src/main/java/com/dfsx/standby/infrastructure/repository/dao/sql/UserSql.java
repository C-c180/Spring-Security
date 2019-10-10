package com.dfsx.standby.infrastructure.repository.dao.sql;

import com.dfsx.standby.infrastructure.entities.UserEntity;

/**
 * Created by YC on 2019/10/8.
 */
public class UserSql {
    public String insert(UserEntity userEntity) {
        String sql = "INSERT INTO user(username,password) VALUES (#{username},#{password})";
        return sql;
    }

    public String findUserByUsername(String username) {
        String sql = "SELECT id,username,password FROM user WHERE username=#{username}";
        return sql;
    }
}
