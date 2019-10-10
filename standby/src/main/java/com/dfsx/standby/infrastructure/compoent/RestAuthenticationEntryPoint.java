package com.dfsx.standby.infrastructure.compoent;

import com.dfsx.standby.infrastructure.utils.JsonUtil;
import com.dfsx.standby.webapi.common.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yangcheng
 * @ClassName:
 * @Description:
 * @date 2019年10月07日 17:35
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(JsonUtil.serializerObject(CommonResult.unauthoried(authException.getMessage())));
        response.getWriter().flush();
    }
}
