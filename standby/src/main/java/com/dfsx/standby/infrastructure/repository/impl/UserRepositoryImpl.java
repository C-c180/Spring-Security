package com.dfsx.standby.infrastructure.repository.impl;

import com.dfsx.standby.infrastructure.entities.UserEntity;
import com.dfsx.standby.infrastructure.repository.UserRepository;
import com.dfsx.standby.infrastructure.repository.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by YC on 2019/9/29.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserDao _userDao;

    @Override
    public void addUser(UserEntity userEntity) {
        _userDao.insert(userEntity);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return _userDao.findUserByUsername(username);
    }
}
