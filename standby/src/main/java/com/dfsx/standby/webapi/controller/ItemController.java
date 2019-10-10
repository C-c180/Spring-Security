package com.dfsx.standby.webapi.controller;

import com.dfsx.standby.infrastructure.serivce.Business.ItemService;
import com.dfsx.standby.webapi.common.CommonPage;
import com.dfsx.standby.webapi.common.CommonResult;
import com.dfsx.standby.webapi.dto.ItemCreateParam;
import com.dfsx.standby.webapi.dto.ItemModifyParam;
import com.dfsx.standby.webapi.framework.BaseContoller;
import com.dfsx.standby.webapi.model.ItemModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by YC on 2019/10/8.
 */
@Api(tags = "事项相关接口", description = "对待办事项的创建修改等接口")
@RestController
public class ItemController extends BaseContoller {
    @Autowired
    private ItemService _itemService;

    @ApiOperation("创建待办事项")
    @RequestMapping(value = "/items/create", method = RequestMethod.POST)
    public CommonResult createItem(@Validated @RequestBody ItemCreateParam itemCreateParam) {
        int count = _itemService.addItem(getUserDetails().getId(), itemCreateParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("完成待办事项")
    @ApiImplicitParam(name = "itemId", value = "事项id", required = true, dataType = "long")
    @RequestMapping(value = "/items/{itemId}/finish", method = RequestMethod.PUT)
    public CommonResult finishItem(@PathVariable("itemId") long itemId) {
        int count = _itemService.finishOrCancelItem(getUserDetails().getId(), itemId, 1);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("取消待办事项")
    @ApiImplicitParam(name = "itemId", value = "事项id", required = true, dataType = "long")
    @RequestMapping(value = "/items/{itemId}/cancel", method = RequestMethod.PUT)
    public CommonResult cancelItem(@PathVariable("itemId") long itemId) {
        int count = _itemService.finishOrCancelItem(getUserDetails().getId(), itemId, 2);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改待办事项")
    @RequestMapping(value = "/items/update", method = RequestMethod.PUT)
    public CommonResult updateItem(@Validated @RequestBody ItemModifyParam itemModifyParam) {
        int count = _itemService.updateItem(itemModifyParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("回收待办事项")
    @ApiImplicitParam(name = "itemIds", value = "事项id集合", required = true, dataType = "long")
    @RequestMapping(value = "/items/recycle", method = RequestMethod.DELETE)
    public CommonResult recycleItem(@RequestBody List<Long> itemIds) {
        int count = _itemService.recycleItem(getUserDetails().getId(), itemIds);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("恢复待办事项")
    @ApiImplicitParam(name = "itemIds", value = "事项id集合", required = true, dataType = "long")
    @RequestMapping(value = "/items/recovery", method = RequestMethod.PUT)
    public CommonResult recoveryItem(@RequestBody List<Long> itemIds) {
        int count = _itemService.recoveryItem(getUserDetails().getId(), itemIds);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据id获取事项")
    @ApiImplicitParam(name = "itemId",value = "事项id",required = true,dataType = "long",paramType = "path")
    @RequestMapping(value = "/items/{itemId}",method = RequestMethod.GET)
    public CommonResult<ItemModel> getItemById(@PathVariable("itemId") long itemId) {
        ItemModel itemModel = _itemService.getItemById(itemId);
        if (itemModel == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(itemModel);
    }

    @ApiOperation("根据条件获取待办事项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "状态", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "TimeStamp", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "TimeStamp", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "起始页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "结束页", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "items/getItems", method = RequestMethod.GET)
    public CommonResult<CommonPage<ItemModel>> getItemByCondition(@RequestParam(value = "state", required = false, defaultValue = "-1") int state,
                                                                  @RequestParam(value = "startTime", required = false) Long startTime,
                                                                  @RequestParam(value = "endTime", required = false) Long endTime,
                                                                  @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", required = false, defaultValue = "18") int size) {
        CommonPage<ItemModel> itemsByCondition = _itemService.getItemsByCondition(getUserDetails().getId(), state,
                startTime == null ? null : new Timestamp(startTime * 1000), endTime == null ? null : new Timestamp(endTime * 1000), page, size);
        return CommonResult.success(itemsByCondition);
    }
}
