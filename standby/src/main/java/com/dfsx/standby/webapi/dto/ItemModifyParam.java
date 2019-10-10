package com.dfsx.standby.webapi.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by YC on 2019/10/10.
 */
@Accessors(prefix = "_")
@Data
public class ItemModifyParam {
    @NotNull(message = "id不能为空")
    private Long _id;
    @NotEmpty(message = "内容不能为空")
    @Size(min = 5, max = 200, message = "内容长度在5到200之间")
    private String _content;
    @NotNull(message = "开始时间不能为空")
    private Long _startTime;
    @NotNull(message = "结束时间布恩那个为空")
    private Long _endTime;
}
