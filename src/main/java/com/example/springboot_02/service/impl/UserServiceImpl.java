package com.example.springboot_02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_02.mapper.UserMapper;
import com.example.springboot_02.pojo.User;
import com.example.springboot_02.service.UserService;
import org.springframework.stereotype.Service;

/**
 * className:UserServiceImpl
 * Package:com.example.springboot_02.service.impl
 * Description:一步一脚印！
 *
 * @Date: 2023/5/1 14:50
 * @Author:2692243932@qq.com
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
