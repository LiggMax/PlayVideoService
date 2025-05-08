package com.ligg.admin.service.impl;

import com.ligg.admin.mapper.AdminVideoMapper;
import com.ligg.admin.service.AdminVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员视频服务实现类
 */
@Service
public class AdminVideoServiceImpl implements AdminVideoService {

    @Autowired
    private AdminVideoMapper adminVideoMapper;

    /**
     * 获取待审核视频列表
     */
    @Override
    public Map<String, Object> getPendingVideos(int offset, int limit) {
        List<Map<String, Object>> videos = adminVideoMapper.selectPendingVideos(offset, limit);
        int total = adminVideoMapper.countPendingVideos();
        
        Map<String, Object> result = new HashMap<>();
        result.put("videos", videos);
        result.put("total", total);
        
        return result;
    }

    /**
     * 获取所有视频列表（可筛选）
     */
    @Override
    public Map<String, Object> getAllVideos(int offset, int limit, Integer status, String keyword) {
        List<Map<String, Object>> videos = adminVideoMapper.selectAllVideos(offset, limit, status, keyword);
        int total = adminVideoMapper.countAllVideos(status, keyword);
        
        Map<String, Object> result = new HashMap<>();
        result.put("videos", videos);
        result.put("total", total);
        
        return result;
    }

    /**
     * 获取视频详情
     */
    @Override
    public Map<String, Object> getVideoDetail(Long id) {
        return adminVideoMapper.selectVideoById(id);
    }

    /**
     * 审核视频
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewVideo(Long id, Integer status, String rejectReason) {
        if (status == null || (status != 1 && status != 3)) {
            return false;
        }
        
        // 当拒绝时，必须有拒绝原因
        if (status == 3 && (rejectReason == null || rejectReason.trim().isEmpty())) {
            return false;
        }
        
        String updateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // 更新草稿视频状态
        int rows = adminVideoMapper.updateDraftVideoStatus(id, status, rejectReason, updateTime);
        
        // 如果是通过审核，则复制到正式视频表
        if (status == 1 && rows > 0) {
            adminVideoMapper.moveDraftToVideo(id);
            // 审核通过后，可以选择性地删除草稿视频
             adminVideoMapper.deleteDraftVideo(id);
        }
        
        return rows > 0;
    }

    /**
     * 删除视频
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteVideo(Long id) {
        // 先尝试删除正式视频
        int rows = adminVideoMapper.deleteVideo(id);
        // 如果没有删除到正式视频，尝试删除草稿视频
        if (rows == 0) {
            rows = adminVideoMapper.deleteDraftVideo(id);
        }
        return rows > 0;
    }
} 