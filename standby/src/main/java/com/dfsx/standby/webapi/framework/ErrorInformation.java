package com.dfsx.standby.webapi.framework;

/**
 * Created by YC on 2019/9/29.
 */
public class ErrorInformation {
    private int _error;
    private String _messgae;

    public int getError() {
        return _error;
    }

    public String getMessgae() {
        return _messgae;
    }

    public ErrorInformation(int _error, String _messgae) {
        this._error = _error;
        this._messgae = _messgae;
    }
}
