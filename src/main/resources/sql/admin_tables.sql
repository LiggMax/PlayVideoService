-- 管理员表
CREATE TABLE IF NOT EXISTS `admin_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- 管理员操作日志表
CREATE TABLE IF NOT EXISTS `admin_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `admin_id` bigint NOT NULL COMMENT '管理员ID',
  `operation` varchar(50) NOT NULL COMMENT '操作类型',
  `content` text COMMENT '操作内容',
  `ip` varchar(50) DEFAULT NULL COMMENT '操作IP',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_admin_id` (`admin_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员操作日志表';

-- 插入默认管理员账号（密码：admin123，MD5加密）
INSERT INTO `admin_user` (`username`, `password`, `nickname`, `status`) 
VALUES ('admin', '0192023a7bbd73250516f069df18b500', '超级管理员', 1); 