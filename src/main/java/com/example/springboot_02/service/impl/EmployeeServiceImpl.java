package com.example.springboot_02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_02.mapper.EmployeeMapper;
import com.example.springboot_02.pojo.Employee;
import com.example.springboot_02.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * className:EmployeeServiceImpl
 * Package:com.example.springboot_02.service.impl
 * Description:一步一脚印！
 *
 * @Date: 2022/12/29 19:10
 * @Author:2692243932@qq.com
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
