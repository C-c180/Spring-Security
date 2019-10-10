package com.dfsx.standby.infrastructure.repository.impl;

import com.dfsx.standby.infrastructure.entities.ItemEntity;
import com.dfsx.standby.infrastructure.repository.ItemRepository;
import com.dfsx.standby.infrastructure.repository.dao.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by YC on 2019/10/8.
 */
@Repository
public class ItemRepositoryImpl implements ItemRepository {

    @Autowired
    private ItemDao _itemDao;

    @Override
    public int addItem(ItemEntity itemEntity) {
        if (itemEntity.getEndTime().getTime() < itemEntity.getStartTime().getTime()) {
            return 0;
        }
        return _itemDao.insert(itemEntity);
    }

    @Override
    public int updateItem(ItemEntity itemEntity) {
        return _itemDao.updateItem(itemEntity);
    }

    @Override
    public List<ItemEntity> findItemByCondition(Map<String, Object> condition) {
        return _itemDao.findItemByCondition(condition);
    }

    @Override
    public List<ItemEntity> findItemByIdsAndState(Long userId, List<Long> itemIds, Integer state) {
        return _itemDao.findItemByIdsAndState(userId, itemIds, state);
    }

    @Override
    public int deleteItem(List<Long> itemIds) {
        return _itemDao.deleteItem(itemIds);
    }
}
