package com.starcpdk.edu.eduservice.service.impl;

import com.starcpdk.edu.eduservice.entity.User;
import com.starcpdk.edu.eduservice.mapper.UserMapper;
import com.starcpdk.edu.eduservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public List<User> seluser(User user,int page,int num) {
        return userMapper.seluser(user,page,num);
    }

    @Override
    public int insuser(User user) {
        return userMapper.insuser(user);
    }
}
