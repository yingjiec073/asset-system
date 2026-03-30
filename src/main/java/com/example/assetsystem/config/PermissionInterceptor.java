package com.example.assetsystem.config;

import com.example.assetsystem.annotation.RequirePermission;
import com.example.assetsystem.context.UserContext;
import com.example.assetsystem.service.rbac.RbacService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    private final RbacService rbacService;

    public PermissionInterceptor(RbacService rbacService) {
        this.rbacService = rbacService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uid = request.getHeader("X-User-Id");
        Long userId = uid == null ? null : Long.parseLong(uid);
        UserContext.setUserId(userId);

        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }
        RequirePermission rp = method.getMethodAnnotation(RequirePermission.class);
        if (rp == null) {
            rp = method.getBeanType().getAnnotation(RequirePermission.class);
        }
        if (rp == null) return true;
        if (userId == null) {
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"message\":\"unauthorized\",\"data\":null}");
            return false;
        }
        Set<String> permissions = rbacService.getUserPermissionCodes(userId);
        if (!permissions.contains(rp.value())) {
            response.setStatus(403);
            response.getWriter().write("{\"code\":403,\"message\":\"permission denied\",\"data\":null}");
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}
