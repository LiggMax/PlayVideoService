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
 * 用户控制器，处理用户信息相关操作
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前登录用户信息
     * @param request 请求对象
     * @return 用户信息
     */
    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentUser(jakarta.servlet.http.HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        // 从请求属性中获取用户信息（由JWT拦截器设置）
        User user = (User) request.getAttribute("user");
        if (user != null) {
            // 不返回密码
            user.setPassword(null);
            result.put("success", true);
            result.put("user", user);
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "未获取到用户信息");
            return ResponseEntity.ok(result);
        }
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