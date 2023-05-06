package com.example.springboot_02.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot_02.pojo.Orders;

/**
 * className:OrdersService
 * Package:com.example.springboot_02.service
 * Description:一步一脚印！
 *
 * @Date: 2023/5/5 0:39
 * @Author:2692243932@qq.com
 */
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
