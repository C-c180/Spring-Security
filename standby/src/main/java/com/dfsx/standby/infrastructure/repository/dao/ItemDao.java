package com.dfsx.standby.infrastructure.repository.dao;

import com.dfsx.standby.infrastructure.entities.ItemEntity;
import com.dfsx.standby.infrastructure.repository.dao.sql.ItemSql;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by YC on 2019/10/8.
 */
public interface ItemDao {

    @InsertProvider(type = ItemSql.class, method = "insert")
    int insert(ItemEntity item);

    @UpdateProvider(type = ItemSql.class, method = "updateSql")
    int updateItem(ItemEntity itemEntity);

    @SelectProvider(type = ItemSql.class, method = "findItemByCondition")
    @Results(id = "itemResultMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "state", column = "state"),
            @Result(property = "startTime", column = "startTime"),
            @Result(property = "endTime", column = "endTime"),
            @Result(property = "userEntity.id", column = "userId")})
    List<ItemEntity> findItemByCondition(Map<String, Object> condition);

    @Select("<script>select id,content,state,startTime,endTime,userId from item where 1=1 and id in" +
            "<foreach collection='itemIds' open='(' separator=',' close=')' item='item' index='index'>" +
            "#{item}" +
            "</foreach>" +
            "<if test='userId!=null'>" +
            "and userId=#{userId}" +
            "</if>" +
            "<if test='state!=null'>" +
            "and state=#{state}" +
            "</if>" +
            "</script>")
    @ResultMap(value = "itemResultMap")
    List<ItemEntity> findItemByIdsAndState(@Param("userId") Long userId,@Param("itemIds") List<Long> itemIds,@Param("state") Integer state);

    @Delete("<script>" +
            "DELETE FROM item WHERE userId IN " +
            "<foreach collection='itemIds' open='(' separator=',' close=')' item='item' index='index'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int deleteItem(@Param("itemIds") List<Long> itemIds);
}
