package com.dfsx.standby.infrastructure.repository.dao.sql;

import com.dfsx.standby.infrastructure.entities.ItemEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by YC on 2019/10/8.
 */
public class ItemSql {
    public String insert(ItemEntity itemEntity) {
        String sql = "INSERT INTO item(content,state,startTime,endTime,userId)VALUES (#{content},#{state},#{startTime},#{endTime},#{userEntity.id})";
        return sql;
    }

    public String updateSql(ItemEntity itemEntity) {
        String sql = "update item set ";
        if (itemEntity.getContent() != null) {
            sql += "content=#{content},";
        }
        if (itemEntity.getState() != -1) {
            sql += "state=#{state},";
        }
        if (itemEntity.getStartTime() != null) {
            sql += "startTime=#{startTime},";
        }
        if (itemEntity.getEndTime() != null) {
            sql += "endTime=#{endTime},";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " where id=#{id} ";
        if (itemEntity.getUserEntity().getId() != 0) {
            sql += "and userId=#{userEntity.id}";
        }
        return sql;
    }

    public String findItemByCondition(Map<String, Object> condition) {
        String sql = "select id,content,state,startTime,endTime,userId from item where userId=#{userId} ";
        if (condition.get("state") != null && !condition.get("state").equals(-1)) {
            sql += "and state=#{state} ";
        }
        if (condition.get("startTime") != null) {
            sql += "and startTime>=#{startTime} ";
        }
        if (condition.get("endTime") != null) {
            sql += "and endTime<=#{endTime} ";
        }
        return sql;
    }

    public String deleteItem(List<Long> itemIds) {
        StringBuilder sql = new StringBuilder();
        sql.append("<script>delete from item where id in (#{item})</script>");

        return sql.toString();
    }
}
