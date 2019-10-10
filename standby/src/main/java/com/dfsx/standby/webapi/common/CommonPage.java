package com.dfsx.standby.webapi.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by YC on 2019/10/8.
 */
@Accessors(prefix = "_")
@Data
public class CommonPage<T> {
    private long _total;
    private List<T> _list;

    /**
     * 转换pageHelper分页
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonPage<T> restPage(long total,List<T> data) {
        CommonPage<T> result = new CommonPage<T>();
        result.setList(data);
        result.setTotal(total);
        return result;
    }
}
