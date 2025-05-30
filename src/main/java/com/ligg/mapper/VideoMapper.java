package com.ligg.mapper;

import com.ligg.admin.entity.Barrage;
import com.ligg.entity.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 视频数据访问接口
 */
@Mapper
public interface VideoMapper {
    
    /**
     * 插入视频记录
     * @param video 视频对象
     * @return 影响的行数
     */
    int insert(Video video);
    
    /**
     * 更新视频记录
     * @param video 视频对象
     * @return 影响的行数
     */
    int update(Video video);
    
    /**
     * 根据ID查询视频
     * @param id 视频ID
     * @return 视频对象
     */
    Video selectById(Long id);
    
    /**
     * 根据用户ID查询视频列表
     * @param userId 用户ID
     * @return 视频列表
     */
    List<Video> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID统计视频数量
     * @param userId 用户ID
     * @return 视频数量
     */
    int countByUserId(Long userId);
    
    /**
     * 根据ID删除视频
     * @param id 视频ID
     * @return 影响的行数
     */
    int deleteById(Long id);
    
    /**
     * 增加视频播放次数
     * @param id 视频ID
     * @return 影响的行数
     */
    int incrementViews(Long id);
    
    /**
     * 增加视频点赞数
     * @param id 视频ID
     * @return 影响的行数
     */
    int incrementLikes(Long id);
    
    /**
     * 根据视频分类查询视频列表
     * @param category 视频分类
     * @param status 视频状态
     * @param offset 起始行
     * @param limit 查询行数
     * @return 视频列表
     */
    List<Video> selectByCategory(@Param("category") String category, @Param("status") Integer status, 
                                @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 查询最新上传的视频
     * @param status 视频状态
     * @param offset 起始行
     * @param limit 查询行数
     * @return 视频列表
     */
    List<Video> selectLatestVideos(@Param("status") Integer status, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 查询热门视频
     * @param status 视频状态
     * @param offset 起始行
     * @param limit 查询行数
     * @return 视频列表
     */
    List<Video> selectPopularVideos(@Param("status") Integer status, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据标题和描述进行模糊搜索
     * @param keyword 关键词
     * @param status 视频状态
     * @param offset 起始行
     * @param limit 查询行数
     * @return 视频列表
     */
    List<Video> searchByKeyword(@Param("keyword") String keyword, @Param("status") Integer status, 
                               @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 统计分类视频数量
     * @param category 视频分类
     * @param status 视频状态
     * @return 视频数量
     */
    int countByCategory(@Param("category") String category, @Param("status") Integer status);
    
    /**
     * 统计有效视频总数
     * @param status 视频状态
     * @return 视频数量
     */
    int countByStatus(@Param("status") Integer status);
    
    /**
     * 统计搜索结果数量
     * @param keyword 关键词
     * @param status 视频状态
     * @return 视频数量
     */
    int countSearchResult(@Param("keyword") String keyword, @Param("status") Integer status);

    /**
     * 插入弹幕记录
     */
    @Insert("insert into barrage(content,time,video_id)" +
            "values (#{content},#{time},#{videoId})")
    void insertBaeeage(Barrage barrage);

    /**
     * 获取弹幕数据
     */
    @Select("select * from barrage where video_id=#{videoId}")
    List<Barrage> selectBarrageByVideoId(Long videoId);
}