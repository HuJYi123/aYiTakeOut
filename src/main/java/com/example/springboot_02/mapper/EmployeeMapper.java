package com.example.springboot_02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot_02.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * className:EmployeeMapper
 * Package:com.example.springboot_02.mapper
 * Description:一步一脚印！
 *
 * @Date: 2022/12/29 19:07
 * @Author:2692243932@qq.com
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
