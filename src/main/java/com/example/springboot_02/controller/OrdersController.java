package com.example.springboot_02.controller;

import com.example.springboot_02.common.R;
import com.example.springboot_02.pojo.Orders;
import com.example.springboot_02.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * className:OrdersController
 * Package:com.example.springboot_02.controller
 * Description:一步一脚印！
 *
 * @Date: 2023/5/5 0:40
 * @Author:2692243932@qq.com
 */
@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;


    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("");
    }
}
