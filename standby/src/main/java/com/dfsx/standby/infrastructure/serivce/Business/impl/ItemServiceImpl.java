package com.dfsx.standby.infrastructure.serivce.Business.impl;

import com.dfsx.standby.infrastructure.entities.ItemEntity;
import com.dfsx.standby.infrastructure.repository.ItemRepository;
import com.dfsx.standby.infrastructure.serivce.Business.ItemService;
import com.dfsx.standby.webapi.common.CommonPage;
import com.dfsx.standby.webapi.dto.ItemCreateParam;
import com.dfsx.standby.webapi.dto.ItemModifyParam;
import com.dfsx.standby.webapi.model.ItemModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by YC on 2019/10/8.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository _itemRepository;

    @Override
    public int addItem(long userId, ItemCreateParam itemCreateParam) {
        ItemEntity itemEntity = new ItemEntity(userId, itemCreateParam);
        return _itemRepository.addItem(itemEntity);
    }

    @Override
    public int finishOrCancelItem(long userId, long itemId, int state) {
        ItemEntity itemEntity = new ItemEntity(userId, itemId, state);
        return _itemRepository.updateItem(itemEntity);
    }

    @Override
    public int updateItem(ItemModifyParam itemModifyParam) {
        ItemEntity itemEntity = new ItemEntity(itemModifyParam);
        return _itemRepository.updateItem(itemEntity);
    }

    @Override
    public int recycleItem(long userId, List<Long> itemIds) {
        itemIds = _itemRepository.findItemByIdsAndState(userId, itemIds, 0).stream().map(ItemEntity::getId).collect(Collectors.toList());
        int count = 0;
        for (Long id : itemIds) {
            count += _itemRepository.updateItem(new ItemEntity(userId, id, 3));
        }
        return count;
    }

    @Override
    public int recoveryItem(long userId, List<Long> itemIds) {
        itemIds = _itemRepository.findItemByIdsAndState(userId, itemIds, 3).stream().map(ItemEntity::getId).collect(Collectors.toList());
        int count = 0;
        for (Long id : itemIds) {
            count += _itemRepository.updateItem(new ItemEntity(userId, id, 0));
        }
        return count;
    }

    @Override
    public int deleteItem(List<Long> itemIds) {
        return _itemRepository.deleteItem(itemIds);
    }

    @Override
    public ItemModel getItemById(long itemId) {
        List<ItemEntity> entities = _itemRepository.findItemByIdsAndState(null, Collections.singletonList(itemId), null);
        if (entities.size() < 0) {
            return null;
        }
        return new ItemModel(entities.get(0));
    }

    @Override
    public CommonPage<ItemModel> getItemsByCondition(long userId, int state, Timestamp startTime, Timestamp endTime, int page, int size) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", userId);
        condition.put("state", state);
        condition.put("startTime", startTime);
        condition.put("endTime", endTime);
        PageHelper.startPage(page, size);
        List<ItemEntity> list = _itemRepository.findItemByCondition(condition);
        PageInfo<ItemEntity> pageInfo = new PageInfo<>(list);
        List<ItemEntity> data = pageInfo.getList();
        List<ItemModel> itemModels = data.stream().map(ItemModel::new).collect(Collectors.toList());
        return CommonPage.restPage(pageInfo.getTotal(), itemModels);
    }
}
