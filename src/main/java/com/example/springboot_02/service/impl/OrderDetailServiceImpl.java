package com.example.springboot_02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_02.mapper.OrderDetailMapper;
import com.example.springboot_02.pojo.OrderDetail;
import com.example.springboot_02.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * className:OrderDetailServiceImpl
 * Package:com.example.springboot_02.service.impl
 * Description:一步一脚印！
 *
 * @Date: 2023/5/5 0:37
 * @Author:2692243932@qq.com
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
