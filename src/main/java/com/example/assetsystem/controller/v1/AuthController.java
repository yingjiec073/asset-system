package com.example.assetsystem.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.assetsystem.dto.ApiResponse;
import com.example.assetsystem.dto.auth.LoginRequest;
import com.example.assetsystem.dto.auth.TokenRefreshRequest;
import com.example.assetsystem.entity.rbac.UserAccount;
import com.example.assetsystem.mapper.rbac.UserAccountMapper;
import com.example.assetsystem.security.JwtService;
import com.example.assetsystem.service.audit.AuditLogService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserAccountMapper userAccountMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuditLogService auditLogService;

    public AuthController(UserAccountMapper userAccountMapper, PasswordEncoder passwordEncoder,
                          JwtService jwtService, AuditLogService auditLogService) {
        this.userAccountMapper = userAccountMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.auditLogService = auditLogService;
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        UserAccount user = userAccountMapper.selectOne(new LambdaQueryWrapper<UserAccount>()
                .eq(UserAccount::getUsername, request.getUsername()));
        if (user == null || user.getEnabled() == null || user.getEnabled() == 0
                || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return ApiResponse.fail(401, "invalid username or password");
        }
        String accessToken = jwtService.createAccessToken(user.getId(), user.getUsername());
        String refreshToken = jwtService.createRefreshToken(user.getId(), user.getUsername());
        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", accessToken);
        data.put("refreshToken", refreshToken);
        data.put("userId", user.getId());
        auditLogService.log(user.getId(), "login", "user login success");
        return ApiResponse.ok(data);
    }

    @PostMapping("/refresh")
    public ApiResponse<Map<String, Object>> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        Map<String, Object> claims = jwtService.parse(request.getRefreshToken());
        Long userId = Long.parseLong(String.valueOf(claims.get("sub")));
        String username = String.valueOf(claims.get("username"));
        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", jwtService.createAccessToken(userId, username));
        data.put("refreshToken", jwtService.createRefreshToken(userId, username));
        return ApiResponse.ok(data);
    }
}
