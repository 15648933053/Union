package com.starcpdk.edu.eduservice.service;

import com.starcpdk.edu.eduservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-27
 */
public interface UserService extends IService<User> {

    List<User> seluser(User user,int page,int num);
    int insuser(User user);
}
