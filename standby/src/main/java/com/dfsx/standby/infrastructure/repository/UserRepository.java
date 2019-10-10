package com.dfsx.standby.infrastructure.repository;

import com.dfsx.standby.infrastructure.entities.UserEntity;

/**
 * Created by YC on 2019/9/29.
 */
public interface UserRepository {
    void addUser(UserEntity userEntity);

    UserEntity findUserByUsername(String username);
}
