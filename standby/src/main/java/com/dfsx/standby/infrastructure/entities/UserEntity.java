package com.dfsx.standby.infrastructure.entities;

import com.dfsx.standby.webapi.dto.UserParam;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by YC on 2019/9/29.
 */
@Accessors(prefix = "_")
@Getter
@Setter
public class UserEntity {
    private long _id;

    private String _username;

    private String _password;

    public UserEntity() {
    }

    public UserEntity(UserParam userParam) {
        this._username = userParam.getUsername();
        this._password= userParam.getPassword();
    }
}
