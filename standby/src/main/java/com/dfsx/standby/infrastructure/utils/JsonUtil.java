package com.dfsx.standby.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * @author yangcheng
 * @ClassName:
 * @Description:
 * @date 2019年10月07日 21:42
 */
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String serializerObject(Object source) throws JsonProcessingException {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper.writeValueAsString(source);
    }
}
