package com.starcpdk.edu.eduservice.controller;


import com.starcpdk.edu.commonutils.R;
import com.starcpdk.edu.eduservice.entity.User;
import com.starcpdk.edu.eduservice.service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RestController
//@RequestMapping("/eduservice/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("seluser")
    private R seluser(HttpServletRequest req, HttpServletResponse resp, @RequestBody User user, @PathVariable("page") int page,@PathVariable("num") int num){
        List<User> seluser = userService.seluser(user,page,num);
        return R.ok().data("rows",seluser).data("total",seluser.size());
    }



}

