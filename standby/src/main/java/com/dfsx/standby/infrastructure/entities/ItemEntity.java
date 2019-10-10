package com.dfsx.standby.infrastructure.entities;

import com.dfsx.standby.webapi.dto.ItemCreateParam;
import com.dfsx.standby.webapi.dto.ItemModifyParam;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * Created by YC on 2019/9/29.
 */
@Accessors(prefix = "_")
@Data
public class ItemEntity {
    private long _id;
    private String _content;
    private int _state = 0;//0未完成，1已完成，2已取消，3以回收
    private Timestamp _startTime;
    private Timestamp _endTime;
    private UserEntity _userEntity = new UserEntity();

    public ItemEntity() {
    }

    public ItemEntity(long userId, ItemCreateParam itemCreateParam) {
        this._content = itemCreateParam.getContent();
        this._startTime = new Timestamp(itemCreateParam.getStartTime()*1000);
        this._endTime = new Timestamp(itemCreateParam.getEndTime()*1000);
        this._userEntity.setId(userId);
    }

    public ItemEntity(long userId,long id, int state) {
        this._id = id;
        this._state = state;
        this._userEntity.setId(userId);
    }

    public ItemEntity(ItemModifyParam itemModifyParam) {
        this._id=itemModifyParam.getId();
        this._content=itemModifyParam.getContent();
        this._startTime = new Timestamp(itemModifyParam.getStartTime()*1000);
        this._endTime = new Timestamp(itemModifyParam.getEndTime()*1000);
    }
}
