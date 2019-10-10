package com.dfsx.standby.webapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by YC on 2019/10/10.
 */
@Accessors(prefix = "_")
@Data
public class ItemCreateParam {
    @ApiModelProperty(name = "content",value = "事项内容",required = true,dataType = "String")
    @NotEmpty(message = "内容不能为空")
    private String _content;
    @ApiModelProperty(name = "startTime",value = "开始时间",required = true,dataType = "TimeStamp")
    @NotNull(message = "开始时间不能为空")
    private Long _startTime;
    @ApiModelProperty(name = "endTime",value = "结束时间",required = true,dataType = "TimeStamp")
    @NotNull(message = "结束时间布恩那个为空")
    private Long _endTime;
}
