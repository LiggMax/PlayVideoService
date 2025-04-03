package com.ligg.admin.utils;

import com.ligg.admin.config.JwtConfig;
import com.ligg.admin.entity.AdminUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtUtils {

    @Autowired
    private JwtConfig jwtConfig;
    
    private SecretKey key;
    
    @PostConstruct
    public void init() {
        // 使用配置的密钥初始化
        String secret = jwtConfig.getSecret();
        if (secret == null || secret.isEmpty()) {
            // 如果配置为空，使用默认值
            secret = "LIGG_PlayVideo_Admin_JWT_Secret_Key_Default";
        }
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成管理员token
     * @param admin 管理员信息
     * @return token
     */
    public String generateToken(AdminUser admin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", admin.getId());
        claims.put("username", admin.getUsername());
        
        String issuer = jwtConfig.getIssuer();
        if (issuer == null || issuer.isEmpty()) {
            issuer = "play-video-admin";
        }
        
        long expiration = jwtConfig.getExpiration();
        if (expiration <= 0) {
            expiration = 7200; // 默认2小时
        }
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key)
                .compact();
    }

    /**
     * 解析token
     * @param token token
     * @return Claims
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验token是否有效
     * @param token token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        Claims claims = parseToken(token);
        return claims != null && !claims.getExpiration().before(new Date());
    }

    /**
     * 从token中获取管理员ID
     * @param token token
     * @return 管理员ID
     */
    public Long getAdminIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? Long.valueOf(claims.get("id").toString()) : null;
    }

    /**
     * 从token中获取用户名
     * @param token token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get("username").toString() : null;
    }
} 