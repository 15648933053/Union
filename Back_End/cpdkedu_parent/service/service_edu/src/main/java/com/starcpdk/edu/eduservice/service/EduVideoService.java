package com.starcpdk.edu.eduservice.service;

import com.starcpdk.edu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-03
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程id删除小节
    void removeVideoByCourseId(String courseId);
}
