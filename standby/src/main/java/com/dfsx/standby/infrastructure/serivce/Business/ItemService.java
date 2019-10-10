package com.dfsx.standby.infrastructure.serivce.Business;

import com.dfsx.standby.webapi.common.CommonPage;
import com.dfsx.standby.webapi.dto.ItemCreateParam;
import com.dfsx.standby.webapi.dto.ItemModifyParam;
import com.dfsx.standby.webapi.model.ItemModel;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by YC on 2019/10/8.
 */
public interface ItemService {

    int addItem(long userId,ItemCreateParam itemCreateParam);

    int finishOrCancelItem(long userId,long itemId, int state);

    int updateItem(ItemModifyParam itemModifyParam);

    int recycleItem(long userId,List<Long> itemIds);

    int recoveryItem(long userId, List<Long> itemIds);

    int deleteItem(List<Long> itemIds);

    ItemModel getItemById(long itemId);

    CommonPage<ItemModel> getItemsByCondition(long userId, int state, Timestamp startTime, Timestamp endTime,int page,int size);
}
