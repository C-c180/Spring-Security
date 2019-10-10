package com.dfsx.standby.infrastructure.repository;

import com.dfsx.standby.infrastructure.entities.ItemEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by YC on 2019/10/8.
 */
public interface ItemRepository {
    int addItem(ItemEntity itemEntity);

    int updateItem(ItemEntity itemEntity);

    List<ItemEntity> findItemByCondition(Map<String, Object> condition);

    List<ItemEntity> findItemByIdsAndState(Long userId, List<Long> itemIds, Integer state);

    int deleteItem(List<Long> itemIds);
}




