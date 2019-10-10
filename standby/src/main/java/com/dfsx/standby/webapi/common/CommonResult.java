package com.dfsx.standby.webapi.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yangcheng
 * @ClassName:
 * @Description:
 * @date 2019年10月07日 15:58
 */
@Accessors(prefix = "_")
@Data
public class CommonResult<T> {
    private int _code;

    private String _message;

    private T _data;

    protected CommonResult() {
    }

    protected CommonResult(int code, String message, T data) {
        this._code = code;
        this._message = message;
        this._data = data;
    }

    /**
     * 返回结构成功
     * @param data 返回数据
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessgae(), data);
    }

    /**
     * 返回结果失败
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed() {
        return new CommonResult<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessgae(), null);
    }

    /**
     * 未登录
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> unauthoried(T data) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessgae(), data);
    }
}
