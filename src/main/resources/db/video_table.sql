-- 创建视频表
CREATE TABLE `video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '视频ID',
  `title` varchar(100) NOT NULL COMMENT '视频标题',
  `description` varchar(1000) DEFAULT NULL COMMENT '视频描述',
  `video_path` varchar(255) NOT NULL COMMENT '视频文件路径',
  `video_url` varchar(255) NOT NULL COMMENT '视频访问URL',
  `cover_path` varchar(255) DEFAULT NULL COMMENT '封面图片路径',
  `cover_url` varchar(255) DEFAULT NULL COMMENT '封面图片URL',
  `duration` int(11) DEFAULT 0 COMMENT '视频时长(秒)',
  `category` varchar(50) DEFAULT NULL COMMENT '视频分类',
  `user_id` bigint(20) NOT NULL COMMENT '上传者ID',
  `uploader_name` varchar(50) DEFAULT NULL COMMENT '上传者名称',
  `views` int(11) DEFAULT 0 COMMENT '播放次数',
  `likes` int(11) DEFAULT 0 COMMENT '点赞数',
  `status` tinyint(4) DEFAULT 1 COMMENT '视频状态：0-处理中，1-正常，2-审核中，3-已下架',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `tags` varchar(200) DEFAULT NULL COMMENT '视频标签，逗号分隔',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频信息表';

-- 初始化一些测试数据
INSERT INTO `video` (`title`, `description`, `video_path`, `video_url`, `cover_path`, `cover_url`, 
                    `duration`, `category`, `user_id`, `uploader_name`, `views`, `likes`, 
                    `status`, `create_time`, `update_time`, `tags`)
VALUES
('春天的风景', '这是一段美丽的春天风景视频', '/videos/spring_scenery.mp4', 'http://localhost:8080/videos/spring_scenery.mp4', 
 '/covers/spring_cover.jpg', 'http://localhost:8080/videos/cover/spring_cover.jpg', 
 180, '风景', 1, '用户1', 1024, 128, 
 1, NOW(), NOW(), '春天,风景,自然'),
 
('编程教程：Java基础', 'Java编程基础入门教程', '/videos/java_basic.mp4', 'http://localhost:8080/videos/java_basic.mp4', 
 '/covers/java_cover.jpg', 'http://localhost:8080/videos/cover/java_cover.jpg', 
 1200, '教育', 2, '程序员小王', 2048, 512, 
 1, NOW(), NOW(), 'Java,编程,教程'),
 
('美食分享：家常菜做法', '简单易学的家常菜做法分享', '/videos/home_cooking.mp4', 'http://localhost:8080/videos/home_cooking.mp4', 
 '/covers/cooking_cover.jpg', 'http://localhost:8080/videos/cover/cooking_cover.jpg', 
 600, '美食', 3, '厨师长', 5120, 1024, 
 1, NOW(), NOW(), '美食,家常菜,烹饪'); 