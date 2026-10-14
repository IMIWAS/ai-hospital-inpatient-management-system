package com.hims.interceptor;

import com.hims.domain.SysUser;
import com.hims.utils.ResultVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行OPTIONS请求
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        // 放行登录接口
        String uri = request.getRequestURI();
        if (uri.contains("/api/user/login")) {
            return true;
        }
        
        // 检查session中是否有用户信息
        HttpSession session = request.getSession(false);
        if (session != null) {
            SysUser user = (SysUser) session.getAttribute("user");
            if (user != null) {
                return true;
            }
        }
        
        // 也可以从请求头获取用户信息
        String userId = request.getHeader("X-User-Id");
        if (userId != null && !userId.isEmpty()) {
            return true;
        }
        
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"msg\":\"未登录或登录已过期\",\"data\":null}");
        return false;
    }
}
