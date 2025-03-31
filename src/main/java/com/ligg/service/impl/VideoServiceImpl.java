package com.ligg.service.impl;

import com.ligg.entity.Video;
import com.ligg.mapper.VideoMapper;
import com.ligg.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 视频服务实现类
 */
@Service
public class VideoServiceImpl implements VideoService {
    
    @Autowired
    private VideoMapper videoRepository;
    
    @Override
    @Transactional
    public Video saveVideo(Video video) {
        // 如果是新视频，设置创建时间
        if (video.getId() == null) {
            video.setCreateTime(LocalDateTime.now());
            // 更新时间
            video.setUpdateTime(LocalDateTime.now());
            // 执行插入
            videoRepository.insert(video);
        } else {
            // 更新时间
            video.setUpdateTime(LocalDateTime.now());
            // 执行更新
            videoRepository.update(video);
        }
        
        return video;
    }
    
    @Override
    public Video findById(Long id) {
        return videoRepository.selectById(id);
    }
    
    @Override
    public List<Video> findByUserId(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        return videoRepository.selectByUserId(userId, offset, size);
    }
    
    @Override
    public int countByUserId(Long userId) {
        return videoRepository.countByUserId(userId);
    }
    
    @Override
    @Transactional
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public Video incrementViews(Long id) {
        videoRepository.incrementViews(id);
        return videoRepository.selectById(id);
    }
    
    @Override
    @Transactional
    public Video incrementLikes(Long id) {
        videoRepository.incrementLikes(id);
        return videoRepository.selectById(id);
    }
    
    @Override
    public Page<Video> findByCategory(String category, int page, int size) {
        int offset = (page - 1) * size;
        List<Video> videos = videoRepository.selectByCategory(category, 1, offset, size);
        int total = videoRepository.countByCategory(category, 1);
        return new PageImpl<>(videos, PageRequest.of(page - 1, size), total);
    }
    
    @Override
    public Page<Video> findLatestVideos(int page, int size) {
        int offset = (page - 1) * size;
        List<Video> videos = videoRepository.selectLatestVideos(1, offset, size);
        int total = videoRepository.countByStatus(1);
        return new PageImpl<>(videos, PageRequest.of(page - 1, size), total);
    }
    
    @Override
    public Page<Video> findPopularVideos(int page, int size) {
        int offset = (page - 1) * size;
        List<Video> videos = videoRepository.selectPopularVideos(1, offset, size);
        int total = videoRepository.countByStatus(1);
        return new PageImpl<>(videos, PageRequest.of(page - 1, size), total);
    }
    
    @Override
    public Page<Video> searchVideos(String keyword, int page, int size) {
        int offset = (page - 1) * size;
        List<Video> videos = videoRepository.searchByKeyword(keyword, 1, offset, size);
        int total = videoRepository.countSearchResult(keyword, 1);
        return new PageImpl<>(videos, PageRequest.of(page - 1, size), total);
    }
} 