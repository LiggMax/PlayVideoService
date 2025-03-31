-- 使用数据库
USE play_video;

-- 删除旧数据
TRUNCATE TABLE `user`;

-- 插入测试数据（密码经过MD5加密，原始密码为123456）
-- 密码使用PasswordEncoder.encode("123456")后的值
INSERT INTO `user` (`username`, `password`, `nickname`, `avatar_url`, `email`, `phone`, `create_time`, `update_time`)
VALUES 
('admin', 'e063e8eb356a8977971227d1d5efa204', '管理员', 'https://via.placeholder.com/150', 'admin@example.com', '13800000000', NOW(), NOW()),
('test', 'e063e8eb356a8977971227d1d5efa204', '测试用户', 'https://via.placeholder.com/150', 'test@example.com', '13900000000', NOW(), NOW()); 