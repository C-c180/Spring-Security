package com.dfsx.standby.webapi.model;

import com.dfsx.standby.infrastructure.entities.UserEntity;
import com.dfsx.standby.webapi.dto.UserParam;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by YC on 2019/9/29.
 */
@Accessors(prefix = "_")
@Data
public class UserModel {
    private long _id;

    private String _username;

    private String _password;

    public UserModel() {
    }

    public UserModel(UserEntity userEntity) {
        this._id = userEntity.getId();
        this._username = userEntity.getUsername();
        this._password = userEntity.getPassword();
    }

    public UserModel(UserParam userParam) {
        this._username = userParam.getUsername();
    }
}
