package com.dfsx.standby.webapi.common;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author yangcheng
 * @ClassName:
 * @Description:
 * @date 2019年10月07日 16:04
 */
@Accessors(prefix = "_")
@Getter
public enum  ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数校验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期");

    private int _code;

    private String _messgae;

    private ResultCode(int code,String messgae) {
        this._code=code;
        this._messgae=messgae;
    }
}
