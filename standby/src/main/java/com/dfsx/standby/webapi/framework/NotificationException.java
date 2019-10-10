package com.dfsx.standby.webapi.framework;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by YC on 2019/9/29.
 */
@Accessors(prefix = "_")
@Getter
public class NotificationException extends Exception {
    private int _code;

    public NotificationException(int errorCode) {
        super(GlobalError.ERRORS.get(errorCode));
        this._code = errorCode;
    }

    public NotificationException(int errorCode, String message) {
        super(message);
        this._code = errorCode;
    }
}
