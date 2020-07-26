package com.starcpdk.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starcpdk.edu.commonutils.R;
import com.starcpdk.edu.eduservice.entity.EduTeacher;
import com.starcpdk.edu.eduservice.entity.vo.TeacherQuery;
import com.starcpdk.edu.eduservice.service.impl.EduTeacherServiceImpl;
import com.starcpdk.edu.service_base.exceptionhandler.MyException;
import io.swagger.annotations.Api;
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
 * 讲师 前端控制器
 * </p>
 *
 * @author 姚云峰
 * @since 2020-06-10
 */

@CrossOrigin
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//访问路径：http://localhost:8001/eduservice/teacher/findAll
public class EduTeacherController {
    //注入service
    @Autowired
    private EduTeacherServiceImpl eduTeacherService;

    //1.查询讲师表中的所有数据
    //rest风格数据
    @GetMapping("findAll")
    @ApiOperation(value = "所有讲师列表")
    public R findAllTeacher() {
        //调用service层方法执行查询所有数据操作
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        if (eduTeachers != null){
            return R.ok().data("items", eduTeachers);
        }else {
            return R.error();
        }
    }

    //2.逻辑删除讲师的方法
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据id删除讲师")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3.分页查询讲师
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacherList(@ApiParam(value = "当前页") @PathVariable long current,
                             @ApiParam(value = "每页显示的记录数") @PathVariable long limit) {
        //new 一个 page对象
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        //调用方法实现分页
        eduTeacherService.page(teacherPage, null);
//        try{
//            int x = 1/0;
//        }catch (Exception e){
//            throw new MyException(2001 , "执行了自定义异常处理");
//        }
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();

        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);

        if (records != null){
            return R.ok().data(map);
        }else {
            return R.error();
        }
    }

    //4.条件查询带分页的方法
    @ApiOperation(value = "多条件组合查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    @ResponseBody
    public R pageTeacherCondition(@ApiParam(value = "当前页") @PathVariable long current,
                                  @ApiParam(value = "每页数量") @PathVariable long limit,
                                  @ApiParam(value = "查询条件") @RequestBody(required = false) TeacherQuery teacherQuery) {//required = false   参数值可以为空
        Page<EduTeacher> teacherPage = new Page<>(current , limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //mybatis 中的动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)){//判断name是否为空或者空字符串
            wrapper.like("name" , name);//数据库字段名   ，    传入的值
        }

        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level" , level);
        }

        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create" , begin);
        }

        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create" , end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        eduTeacherService.page(teacherPage , wrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();

        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        if (records != null){
            return R.ok().data(map);
        }else {
            return R.error();
        }
    }

    //5.添加讲师接口的方法
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeather")
    public R addTeather(@ApiParam(value = "讲师对象") @RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //6.根据讲师id进行查询
    @ApiOperation(value = "根据讲师id查询讲师信息")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(value = "讲师id") @PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        if (eduTeacher != null){
            return R.ok().data("teacher",eduTeacher);
        }else {
            return R.error();
        }
    }

    //7.讲师修改
    @ApiOperation(value = "讲师修改")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(value = "讲师对象") @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

