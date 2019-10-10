package com.dfsx.standby.infrastructure.repository.dao;

import com.dfsx.standby.infrastructure.entities.UserEntity;
import com.dfsx.standby.infrastructure.repository.dao.sql.UserSql;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * Created by YC on 2019/9/29.
 */
public interface UserDao {

    @InsertProvider(type = UserSql.class,method = "insert")
    void insert(UserEntity userEntity);

    @SelectProvider(type = UserSql.class,method = "findUserByUsername")
    UserEntity findUserByUsername(String username);
}
