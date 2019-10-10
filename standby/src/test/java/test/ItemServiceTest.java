package test;

import com.dfsx.standby.GlobalConfig;
import com.dfsx.standby.infrastructure.serivce.Business.ItemService;
import com.dfsx.standby.webapi.common.CommonPage;
import com.dfsx.standby.webapi.dto.ItemCreateParam;
import com.dfsx.standby.webapi.model.ItemModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

/**
 * Created by YC on 2019/10/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GlobalConfig.class)
public class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @Test
    public void addItem() throws Exception {
        ItemCreateParam itemCreateParam = new ItemCreateParam();
        itemCreateParam.setContent("测试");
        itemCreateParam.setStartTime(System.currentTimeMillis());
        itemCreateParam.setEndTime(System.currentTimeMillis()+24*60*60*1000);
        int i = itemService.addItem(1, itemCreateParam);
        System.out.println(i);
    }

    @Test
    public void finishOrCancelItem() throws Exception {
        int i = itemService.finishOrCancelItem(1, 2,0);
        System.out.println(i);
    }

    @Test
    public void updateItem() throws Exception {

    }

    @Test
    public void recoveryItem() throws Exception {
        int i = itemService.recoveryItem(1,Collections.singletonList(2L));
    }

    @Test
    public void deleteItem() throws Exception {
        itemService.deleteItem(Collections.singletonList(1L));
    }

    @Test
    public void getItemsByCondition() throws Exception {
        CommonPage<ItemModel> itemsByCondition = itemService.getItemsByCondition(1, 0, null, null, 1, 18);
    }

}