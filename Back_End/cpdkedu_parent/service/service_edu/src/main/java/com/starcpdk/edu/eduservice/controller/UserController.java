package com.starcpdk.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starcpdk.edu.commonutils.R;
import com.starcpdk.edu.eduservice.entity.User;
import com.starcpdk.edu.eduservice.entity.vo.TeacherQuery;
import com.starcpdk.edu.eduservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-27
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/user")
public class UserController {
    @Autowired
    private UserService userService;


    //1. 添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeather")
    public R addTeather(@ApiParam(value = "讲师对象") @RequestBody User eduTeacher){
        boolean save = userService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //2.条件查询带分页的方法
    @ApiOperation(value = "多条件组合查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    @ResponseBody
    public R pageTeacherCondition(@ApiParam(value = "当前页") @PathVariable long current,
                                  @ApiParam(value = "每页数量") @PathVariable long limit,
                                  @ApiParam(value = "查询条件") @RequestBody(required = false) TeacherQuery teacherQuery) {//required = false   参数值可以为空
        Page<User> teacherPage = new Page<>(current , limit);
        System.out.println(current + "  "  + limit);

        //构建条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //mybatis 中的动态sql
        String name = teacherQuery.getName();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)){//判断name是否为空或者空字符串name
            wrapper.like("name" , name);//数据库字段名   ，    传入的值
        }

        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("uni_create" , begin);
        }

        if (!StringUtils.isEmpty(end)){
            wrapper.le("uni_create" , end);
        }

        //排序
        wrapper.orderByDesc("uni_create");

        //调用方法实现条件查询分页
        userService.page(teacherPage , wrapper);
        long total = teacherPage.getTotal();
        List<User> records = teacherPage.getRecords();

        System.out.println(records);

        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        if (records != null){
            return R.ok().data(map);
        }else {
            return R.error();
        }
    }

    //3.逻辑删除讲师的方法
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据id删除讲师")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师oppenid", required = true) @PathVariable String id) {
        boolean flag = userService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //6.根据讲师id进行查询
    @ApiOperation(value = "根据讲师id查询讲师信息")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(value = "讲师id") @PathVariable String id){
        User eduTeacher = userService.getById(id);
        if (eduTeacher != null){
            return R.ok().data("teacher",eduTeacher);
        }else {
            return R.error();
        }
    }

    //7.讲师修改
    @ApiOperation(value = "讲师修改")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(value = "讲师对象") @RequestBody User eduTeacher){
        boolean flag = userService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

