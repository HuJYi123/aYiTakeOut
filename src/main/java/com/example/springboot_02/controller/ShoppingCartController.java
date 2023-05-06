package com.example.springboot_02.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springboot_02.common.BaseContext;
import com.example.springboot_02.common.R;
import com.example.springboot_02.pojo.ShoppingCart;
import com.example.springboot_02.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * className:ShoppingCartController
 * Package:com.example.springboot_02.controller
 * Description:一步一脚印！
 *
 * @Date: 2023/5/1 18:21
 * @Author:2692243932@qq.com
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        System.out.println("sasa:"+ JSON.toJSONString(shoppingCart));
        //设置用户ID
        Long id = BaseContext.getCurrentId();
        shoppingCart.setUserId(id);

        //查询当前菜品或者套餐是否在购物车中
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,id);
        if (dishId != null){
            //添加到购物车的是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            //添加到购物车的是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart one = shoppingCartService.getOne(lambdaQueryWrapper);
        if (one != null) {
            //判断当前菜品或套餐是否在购物车中，在则加1
            Integer number = one.getNumber();
            one.setNumber(number + 1);
            shoppingCartService.updateById(one);
        }else {
            //如果不存在，则加入购物车中
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            one = shoppingCart;
        }
        return R.success(one);
    }


    @GetMapping("/list")
    public R<List<ShoppingCart>> get(){
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        lambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    /**
     * 减少购物车数量
     * @return
     */
    @PostMapping("/sub")
    public R<String> sub(@RequestBody Map<String,String> map){
        String dishId = map.get("dishId");
        System.out.println("菜品ID"+dishId);
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getDishId,dishId);
        ShoppingCart one = shoppingCartService.getOne(lambdaQueryWrapper);
        if (one != null){
            Integer number = one.getNumber();
            one.setNumber(number - 1);
            System.out.println("减数量成功");
            shoppingCartService.updateById(one);
            return R.success("减数量成功");
        }
        return R.success("减数量失败");
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(lambdaQueryWrapper);
        return R.success("清空成功");
    }
}
