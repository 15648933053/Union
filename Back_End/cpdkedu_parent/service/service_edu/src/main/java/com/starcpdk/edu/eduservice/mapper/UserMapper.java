package com.starcpdk.edu.eduservice.mapper;

import com.starcpdk.edu.eduservice.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 姚云峰
 * @since 2020-07-27
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
