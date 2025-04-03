package com.ligg.admin.service;

import java.util.Map;

/**
 * 管理员视频服务接口
 */
public interface AdminVideoService {

    /**
     * 获取待审核视频列表
     *
     * @param offset 偏移量
     * @param limit 数量限制
     * @return 视频列表及总数
     */
    Map<String, Object> getPendingVideos(int offset, int limit);

    /**
     * 获取所有视频列表（可筛选）
     *
     * @param offset 偏移量
     * @param limit 数量限制
     * @param status 状态筛选（可为null）
     * @param keyword 关键词（可为null）
     * @return 视频列表及总数
     */
    Map<String, Object> getAllVideos(int offset, int limit, Integer status, String keyword);

    /**
     * 获取视频详情
     *
     * @param id 视频ID
     * @return 视频详情
     */
    Map<String, Object> getVideoDetail(Long id);

    /**
     * 审核视频
     *
     * @param id 视频ID
     * @param status 审核状态（1-通过，3-拒绝）
     * @param rejectReason 拒绝原因（当status=3时需要）
     * @return 是否成功
     */
    boolean reviewVideo(Long id, Integer status, String rejectReason);

    /**
     * 删除视频
     *
     * @param id 视频ID
     * @return 是否成功
     */
    boolean deleteVideo(Long id);
} 