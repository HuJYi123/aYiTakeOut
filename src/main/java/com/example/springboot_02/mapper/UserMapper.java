package com.example.springboot_02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot_02.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * className:UserMapper
 * Package:com.example.springboot_02.mapper
 * Description:一步一脚印！
 *
 * @Date: 2023/5/1 14:49
 * @Author:2692243932@qq.com
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
