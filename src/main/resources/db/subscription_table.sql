-- 创建用户关注表
CREATE TABLE IF NOT EXISTS `user_subscription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关注ID',
  `user_id` bigint(20) NOT NULL COMMENT '关注者ID',
  `target_user_id` bigint(20) NOT NULL COMMENT '被关注者ID',
  `create_time` datetime NOT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_target` (`user_id`, `target_user_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_target_user_id` (`target_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户关注表';

-- 为用户表添加关注数和粉丝数字段
ALTER TABLE `user` 
ADD COLUMN `subscription_count` int(11) DEFAULT 0 COMMENT '关注数',
ADD COLUMN `subscriber_count` int(11) DEFAULT 0 COMMENT '粉丝数'; 