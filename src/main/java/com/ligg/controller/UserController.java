package com.ligg.controller;

import com.ligg.entity.User;
import com.ligg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param loginInfo 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginInfo) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        
        log.info("用户登录: {}", username);
        
        User user = userService.login(username, password);
        Map<String, Object> result = new HashMap<>();
        
        if (user != null) {
            // 登录成功，返回用户信息（密码置空）
            user.setPassword(null);
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("user", user);
            return ResponseEntity.ok(result);
        } else {
            // 登录失败
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        log.info("用户注册: {}", user.getUsername());
        
        Map<String, Object> result = new HashMap<>();
        
        // 检查用户名是否已存在
        if (userService.getUserByUsername(user.getUsername()) != null) {
            result.put("success", false);
            result.put("message", "用户名已存在");
            return ResponseEntity.ok(result);
        }
        
        boolean success = userService.register(user);
        
        if (success) {
            result.put("success", true);
            result.put("message", "注册成功");
        } else {
            result.put("success", false);
            result.put("message", "注册失败");
        }
        
        return ResponseEntity.ok(result);
    }

    /**
     * 获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long id) {
        log.info("获取用户信息: {}", id);
        
        User user = userService.getUserById(id);
        if (user != null) {
            // 不返回密码
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 更新用户信息
     * @param id 用户ID
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User user) {
        log.info("更新用户信息: {}", id);
        
        user.setId(id);
        boolean success = userService.updateUser(user);
        
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("success", true);
            result.put("message", "更新成功");
        } else {
            result.put("success", false);
            result.put("message", "更新失败");
        }
        
        return ResponseEntity.ok(result);
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        log.info("删除用户: {}", id);
        
        boolean success = userService.deleteUser(id);
        
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("success", true);
            result.put("message", "删除成功");
        } else {
            result.put("success", false);
            result.put("message", "删除失败");
        }
        
        return ResponseEntity.ok(result);
    }

    /**
     * 获取所有用户
     * @return 用户列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("获取所有用户");
        
        List<User> users = userService.getAllUsers();
        // 不返回密码
        users.forEach(user -> user.setPassword(null));
        
        return ResponseEntity.ok(users);
    }
} 