package com.starcpdk.edu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starcpdk.edu.commonutils.R;
import com.starcpdk.edu.eduservice.entity.Active;
import com.starcpdk.edu.eduservice.entity.User;
import com.starcpdk.edu.eduservice.entity.vo.ActiveQuery;
import com.starcpdk.edu.eduservice.entity.vo.TeacherQuery;
import com.starcpdk.edu.eduservice.service.ActiveService;
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
 * 前端控制器
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-27
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/active")
public class ActiveController {

    @Autowired
    private ActiveService activeService;

    //1. 添加活动基本信息的方法
    @ApiOperation(value = "添加活动")
    @PostMapping("/addActive")
    public R addActive(@RequestBody Active active) {
        //返回添加之后的课程id , 为了后面的添加大纲使用
        boolean save = activeService.save(active);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //2.条件查询带分页的方法
    @ApiOperation(value = "多条件组合查询讲师")
    @PostMapping("pageActiveCondition/{current}/{limit}")
    @ResponseBody
    public R pageActiveCondition(@ApiParam(value = "当前页") @PathVariable long current,
                                  @ApiParam(value = "每页数量") @PathVariable long limit,
                                  @ApiParam(value = "查询条件") @RequestBody(required = false) ActiveQuery activeQuery) {//required = false   参数值可以为空
        Page<Active> activePage = new Page<>(current , limit);

        //构建条件
        QueryWrapper<Active> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //mybatis 中的动态sql
        String name = activeQuery.getName();
        String date = activeQuery.getDate();
        String score = activeQuery.getScore();

        if (!StringUtils.isEmpty(name)){//判断name是否为空或者空字符串name
            wrapper.like("name" , name);//数据库字段名   ，    传入的值
        }

        if (!StringUtils.isEmpty(date)){
            wrapper.ge("date" , date);
        }

        if (!StringUtils.isEmpty(score)){
            wrapper.eq("score" , score);
        }

        //排序
        wrapper.orderByDesc("date");

        //调用方法实现条件查询分页
        activeService.page(activePage , wrapper);
        long total = activePage.getTotal();
        List<Active> records = activePage.getRecords();

        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        if (records != null){
            return R.ok().data(map);
        }else {
            return R.error();
        }
    }

    //3.逻辑删除活动的方法
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据id删除讲师")
    public R removeActive(@ApiParam(name = "id", value = "讲师oppenid", required = true) @PathVariable String id) {
        boolean flag = activeService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //6.根据活动id进行查询
    @ApiOperation(value = "根据讲师id查询讲师信息")
    @GetMapping("getActive/{id}")
    public R getActive(@ApiParam(value = "讲师id") @PathVariable String id){
        Active active = activeService.getById(id);
        if (active != null){
            return R.ok().data("active",active);
        }else {
            return R.error();
        }
    }

    //7.讲师活动信息
    @ApiOperation(value = "讲师修改")
    @PostMapping("updateActive")
    public R updateActive(@ApiParam(value = "讲师对象") @RequestBody Active active){
        boolean flag = activeService.updateById(active);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

