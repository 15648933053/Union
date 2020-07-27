package com.starcpdk.edu.eduservice.service.impl;

import com.starcpdk.edu.eduservice.entity.User;
import com.starcpdk.edu.eduservice.mapper.UserMapper;
import com.starcpdk.edu.eduservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 内农大计算机院
 * @since 2020-07-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}