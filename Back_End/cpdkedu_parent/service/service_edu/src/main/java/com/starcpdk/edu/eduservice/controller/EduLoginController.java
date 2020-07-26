package com.starcpdk.edu.eduservice.controller;

import com.starcpdk.edu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@CrossOrigin//解决跨域问题
@Api(description = "讲师模拟登录")
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    //login
    @ApiOperation(value = "模拟登陆")
    @PostMapping("login")
    public R login(){
        return R.ok().data("taken" , "admin");
    }

    //info
    @ApiOperation(value = "模拟登陆返回信息数据")
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles" , "[admin]").data("name" , "admin").data("avatar" , "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3573279319,4077916197&fm=26&gp=0.jpg");
    }
}
