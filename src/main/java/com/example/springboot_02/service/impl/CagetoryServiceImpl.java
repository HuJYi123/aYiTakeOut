package com.example.springboot_02.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot_02.common.CustomException;
import com.example.springboot_02.mapper.CagetoryMapper;
import com.example.springboot_02.pojo.Category;
import com.example.springboot_02.pojo.Dish;
import com.example.springboot_02.pojo.Setmeal;
import com.example.springboot_02.service.CagetoryService;
import com.example.springboot_02.service.DishService;
import com.example.springboot_02.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * className:CagetoryServiceImpl
 * Package:com.example.springboot_02.service.impl
 * Description:一步一脚印！
 *
 * @Date: 2022/12/30 17:04
 * @Author:2692243932@qq.com
 */
@Service
public class CagetoryServiceImpl extends ServiceImpl<CagetoryMapper, Category> implements CagetoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id值进行删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if(count1>0){
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //查询是否已经关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2>0){
            //已经关联套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除
        super.removeById(id);
    }
}
