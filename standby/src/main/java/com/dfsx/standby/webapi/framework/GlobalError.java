package com.dfsx.standby.webapi.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YC on 2019/9/29.
 */
public class GlobalError {
    public static final Map<Integer, String> ERRORS = new HashMap<>();

    private GlobalError() {
    }

    public static class ErrorsCode {
        public static final int USERNAME_EXIST = 401;
    }

    static {
        ERRORS.put(ErrorsCode.USERNAME_EXIST, "用户名已存在");
    }
}
