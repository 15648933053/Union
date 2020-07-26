package com.starcpdk.edu.eduservice.service.impl;

import com.starcpdk.edu.eduservice.entity.EduCourse;
import com.starcpdk.edu.eduservice.entity.EduCourseDescription;
import com.starcpdk.edu.eduservice.entity.EduVideo;
import com.starcpdk.edu.eduservice.entity.vo.CourseInfoVo;
import com.starcpdk.edu.eduservice.entity.vo.CoursePublishVo;
import com.starcpdk.edu.eduservice.mapper.EduCourseMapper;
import com.starcpdk.edu.eduservice.service.EduChapterService;
import com.starcpdk.edu.eduservice.service.EduCourseDescriptionService;
import com.starcpdk.edu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starcpdk.edu.eduservice.service.EduVideoService;
import com.starcpdk.edu.service_base.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-03
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述的注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程简介表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        //将courseInfoVo对象转换为eduCourse对象
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert <= 0) {
            //添加失败
            throw new MyException(20001, "添加课程信息失败");
        }

        //获取添加后的课程id
        String cid = eduCourse.getId();

        //2.向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, courseDescription);
        //设置描述id就是课程id
        courseDescription.setId(cid);
        boolean save = courseDescriptionService.save(courseDescription);
        if (!save) {
            throw new MyException(20001, "添加课程描述失败");
        }
        return cid;
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        CourseInfoVo courseInfoVo = new CourseInfoVo();

        //1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        System.out.println("啊哈哈哈哈哈哈哈哈哈" + eduCourse + "id值为" + courseId);
        BeanUtils.copyProperties(eduCourse , courseInfoVo);

        System.out.println("courseInfo的值" + courseInfoVo);

        //2.查询描述表
        EduCourseDescription eduCourseDescription = courseDescriptionService.getById(courseId);

        //封装数据
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1. 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);

        if (update == 0){
            throw new MyException(20001 , "修改课程表失败");
        }

        //2. 修改课程描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo , eduCourseDescription);
        boolean update1 = courseDescriptionService.updateById(eduCourseDescription);
        if (!update1){
            throw new MyException(20001 , "修改课程信息表失败");
        }

    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public boolean removeCourse(String courseId) {

        //根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);

        //根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //根据课程id删除课程描述
        courseDescriptionService.removeById(courseId);

        //根据课程id删除课程本身
        int delete = baseMapper.deleteById(courseId);
        System.out.println(delete);
        if (delete == 0) {
            throw new MyException(20001 , "删除失败");
        }else {
            return true;
        }
    }
}


