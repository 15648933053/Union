package com.starcpdk.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starcpdk.edu.commonutils.R;
import com.starcpdk.edu.eduservice.entity.EduCourse;
import com.starcpdk.edu.eduservice.entity.vo.CourseInfoVo;
import com.starcpdk.edu.eduservice.entity.vo.CoursePublishVo;
import com.starcpdk.edu.eduservice.entity.vo.CourseQuery;
import com.starcpdk.edu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-03
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
@Api(description = "课程信息管理")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //添加课程基本信息的方法
    @ApiOperation(value = "添加课程信息")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后的课程id , 为了后面的添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //根据课程id查询课程基本信息
    @ApiOperation(value = "根据课程id查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @ApiOperation(value = "根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @ApiOperation(value = "课程最终发布,修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        boolean update = courseService.updateById(eduCourse);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //课程列表接口

    //获取所有课程列表
    @ApiOperation(value = "获取所有课程列表")
    @GetMapping("getAllCourse")
    public R getAllCourse() {
        List<EduCourse> eduCourseList = courseService.list(null);
        if (eduCourseList != null) {
            return R.ok().data("courseList", eduCourseList);
        } else {
            return R.error();
        }
    }

    //条件查询带分页的方法
    @ApiOperation(value = "条件查询带分页的方法")
    @PostMapping("getCourseByPage/{current}/{limit}")
    public R getCourseByPage(@ApiParam(value = "当前页") @PathVariable long current,
                             @ApiParam(value = "每页数量") @PathVariable long limit,
                             @ApiParam(value = "查询条件") @RequestBody CourseQuery courseQuery) {

        Page<EduCourse> coursePage = new Page<>(current, limit);

        //构造条件
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();

        //多条件组合查询
        //mybatis 中的动态sql
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(courseQuery.getTitle())){
            courseQueryWrapper.like("title" , title);
        }

        if (!StringUtils.isEmpty(courseQuery.getTeacherId())){
            courseQueryWrapper.eq("teacher_id" , teacherId);
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())){
            courseQueryWrapper.eq("subject_parent_id" , subjectParentId);
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())){
            courseQueryWrapper.eq("subject_id" , subjectId);
        }

        if (!StringUtils.isEmpty(courseQuery.getStatus())){
            if ("0".equals(status)){
                status = "Draft";
            }else {
                status = "Normal";
            }
            courseQueryWrapper.eq("status" , status);
        }

        //调用方法实现条件查询分页
        courseService.page(coursePage , courseQueryWrapper);
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();

        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);

        if (records != null){
            return R.ok().data(map);
        }else {
            return R.error();
        }
    }

    //删课程除
    @ApiOperation(value = "删除课程")
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        boolean remove = courseService.removeCourse(courseId);
        if (remove){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

