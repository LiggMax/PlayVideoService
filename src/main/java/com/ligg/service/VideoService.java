package com.ligg.service;

import com.ligg.entity.Video;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 视频服务接口
 */
public interface VideoService {
    
    /**
     * 保存视频信息
     * @param video 视频对象
     * @return 保存后的视频对象
     */
    Video saveVideo(Video video);
    
    /**
     * 根据ID查询视频
     * @param id 视频ID
     * @return 视频对象
     */
    Video findById(Long id);
    
    /**
     * 根据用户ID查询视频列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页数量
     * @return 视频列表
     */
    List<Video> findByUserId(Long userId, int page, int size);
    
    /**
     * 统计用户上传的视频数量
     * @param userId 用户ID
     * @return 视频数量
     */
    int countByUserId(Long userId);
    
    /**
     * 删除视频
     * @param id 视频ID
     */
    void deleteVideo(Long id);
    
    /**
     * 更新视频浏览量
     * @param id 视频ID
     * @return 更新后的视频对象
     */
    Video incrementViews(Long id);
    
    /**
     * 更新视频点赞量
     * @param id 视频ID
     * @return 更新后的视频对象
     */
    Video incrementLikes(Long id);
    
    /**
     * 根据分类查询视频
     * @param category 分类
     * @param page 页码
     * @param size 每页数量
     * @return 视频分页结果
     */
    Page<Video> findByCategory(String category, int page, int size);
    
    /**
     * 获取最新视频
     * @param page 页码
     * @param size 每页数量
     * @return 视频分页结果
     */
    Page<Video> findLatestVideos(int page, int size);
    
    /**
     * 获取热门视频
     * @param page 页码
     * @param size 每页数量
     * @return 视频分页结果
     */
    Page<Video> findPopularVideos(int page, int size);
    
    /**
     * 搜索视频
     * @param keyword 关键词
     * @param page 页码
     * @param size 每页数量
     * @return 视频分页结果
     */
    Page<Video> searchVideos(String keyword, int page, int size);
} 