package com.example.springboot_02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_02.mapper.ShoppingCartMapper;
import com.example.springboot_02.pojo.ShoppingCart;
import com.example.springboot_02.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * className:ShoppingCartServiceImpl
 * Package:com.example.springboot_02.service.impl
 * Description:一步一脚印！
 *
 * @Date: 2023/5/1 18:20
 * @Author:2692243932@qq.com
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
