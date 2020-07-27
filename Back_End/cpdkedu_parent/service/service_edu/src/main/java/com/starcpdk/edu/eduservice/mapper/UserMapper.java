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

    List<User> seluser(User user,int page,int num);

    @Insert("insert into user values(#{openid},name,birth,'0','0',#{uni_create},#{uni_modified})")
    int insuser(User user);

    @Delete("delete from user where openid = #{openid}")
    int deluser(User user);




}
