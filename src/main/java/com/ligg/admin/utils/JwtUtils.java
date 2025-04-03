package com.ligg.admin.utils;

import com.ligg.admin.config.JwtConfig;
import com.ligg.admin.entity.AdminUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    /**
     * 生成管理员token
     * @param admin 管理员信息
     * @return token
     */
    public String generateToken(AdminUser admin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", admin.getId());
        claims.put("username", admin.getUsername());
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }

    /**
     * 解析token
     * @param token token
     * @return Claims
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret())
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