package com.dfsx.standby.webapi.model;

import com.dfsx.standby.infrastructure.entities.ItemEntity;
import com.dfsx.standby.webapi.Validated.ItemGroup;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by YC on 2019/10/8.
 */
@Accessors(prefix = "_")
@Data
public class ItemModel {
    @NotNull(message = "id不能为空",groups = ItemGroup.Update.class)
    private Long _id;
    @NotEmpty(message = "内容不能为空", groups = {ItemGroup.Create.class, ItemGroup.Update.class})
    @Size(min = 5, max = 200, message = "内容长度在5到200之间", groups = {ItemGroup.class})
    private String _content;
    private int _state = -1;
    @NotNull(message = "开始时间不能为空", groups = {ItemGroup.Create.class})
    private Long _startTime;
    @NotNull(message = "结束时间布恩那个为空", groups = {ItemGroup.Create.class})
    private Long _endTime;

    public ItemModel() {
    }

    public ItemModel(ItemEntity itemEntity) {
        this._id = itemEntity.getId();
        this._content = itemEntity.getContent();
        this._state = itemEntity.getState();
        this._startTime = itemEntity.getStartTime().getTime();
        this._endTime = itemEntity.getEndTime().getTime();
    }
}
