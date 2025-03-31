package com.ligg.interceptor;

import com.ligg.entity.User;
import com.ligg.service.UserService;
import com.ligg.util.JwtTokenUtil;
import com.ligg.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT认证拦截器
 */
@Component
@Slf4j
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;
    
    @Autowired
    private RedisUtil redisUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头中获取令牌
        final String requestHeader = request.getHeader(tokenHeader);
        
        // 如果请求头中没有令牌，返回401
        if (requestHeader == null || !requestHeader.startsWith(tokenPrefix)) {
            log.error("令牌缺失或格式不正确");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 从请求头中获取令牌
        String token = requestHeader.substring(tokenPrefix.length());
        
        try {
            // 从令牌中获取用户名
            String username = jwtTokenUtil.getUsernameFromToken(token);
            
            // 检查Redis中是否存在对应的令牌
            String redisKey = "user:token:" + username;
            String storedToken = (String) redisUtil.get(redisKey);
            
            // 如果Redis中没有令牌或令牌不匹配，返回401
            if (storedToken == null || !storedToken.equals(token)) {
                log.error("令牌已失效或已被注销");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            // 从数据库中获取用户信息
            User user = userService.getUserByUsername(username);
            
            // 如果用户不存在，返回401
            if (user == null) {
                log.error("用户不存在");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            // 验证令牌是否有效
            if (!jwtTokenUtil.validateToken(token, user)) {
                log.error("令牌无效");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            // 将用户信息放入请求属性中，供后续使用
            request.setAttribute("user", user);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("令牌已过期", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        } catch (SignatureException | MalformedJwtException e) {
            log.error("令牌签名无效或格式错误", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        } catch (Exception e) {
            log.error("令牌验证异常", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }
    }
} 