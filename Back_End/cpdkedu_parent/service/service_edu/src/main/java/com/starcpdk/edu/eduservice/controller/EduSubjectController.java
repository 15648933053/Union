package com.starcpdk.edu.eduservice.controller;


import com.starcpdk.edu.commonutils.R;
import com.starcpdk.edu.eduservice.entity.subject.OneSubject;
import com.starcpdk.edu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-01
 */
@Api(description = "课程分类管理模块")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取到上传的文件 , 把文件内容读取出来
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的excel文件
        subjectService.saveSubject(file , subjectService);
        return R.ok();
    }

    //课程分类的列表功能(树形结构)
    @ApiOperation(value = "课程分类列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //list集合中的泛型是一级分类 , 因为一级分类中含有多个二级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list" , list);
    }

}

