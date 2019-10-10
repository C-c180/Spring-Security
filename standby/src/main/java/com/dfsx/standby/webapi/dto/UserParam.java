package com.dfsx.standby.webapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by YC on 2019/10/10.
 */
@Accessors(prefix = "_")
@Data
public class UserParam {
    @ApiModelProperty(value = "用户名", dataType = "String", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String _username;
    @ApiModelProperty(value = "密码", dataType = "String", required = true)
    @NotEmpty(message = "密码不能为空")
    private String _password;
}
