package com.agentproject.auth.controller;

import com.agentproject.auth.dto.LoginRequest;
import com.agentproject.auth.dto.LoginResponse;
import com.agentproject.auth.util.JwtUtil;
import com.agentproject.common.exception.BusinessException;
import com.agentproject.common.response.Result;
import com.agentproject.system.entity.User;
import com.agentproject.system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("用户名或密码错误"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        if (!user.isEnabled()) {
            throw new BusinessException("账号已被禁用");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        return Result.success(new LoginResponse(token, user.getId(), user.getUsername()));
    }

    @GetMapping("/me")
    public Result<User> getCurrentUser(@RequestAttribute(value = "username", required = false) String username) {
        // username comes from the JWT filter's authentication principal
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Result.unauthorized("未登录");
        }
        String name = auth.getName();
        User user = userService.findByUsername(name)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        return Result.success(user);
    }
}
