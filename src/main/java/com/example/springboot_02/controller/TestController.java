package com.example.springboot_02.controller;

import com.example.springboot_02.pojo.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * className:TestController
 * Package:com.example.springboot_02.controller
 * Description:一步一脚印！
 *
 * @Date: 2023/3/17 19:11
 * @Author:2692243932@qq.com
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/two")
    public String test(){
        Employee employee = new Employee();
        employee.setUsername("胡俊奕");
        employee.setPassword("123456");
        System.out.println("666666666666");
        return "employee";
    }
}
