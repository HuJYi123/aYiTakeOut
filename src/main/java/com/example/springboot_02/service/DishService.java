package com.example.springboot_02.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot_02.dto.DishDto;
import com.example.springboot_02.pojo.Dish;

import java.util.List;

/**
 * className:DishService
 * Package:com.example.springboot_02.service
 * Description:一步一脚印！
 *
 * @Date: 2023/1/1 15:04
 * @Author:2692243932@qq.com
 */
public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品和对应的口味信息，涉及两张表
    public void saveWithFlavor(DishDto dishDto);

    //根据ID查询菜品和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息跟口味信息
    public void updateWithFlavor(DishDto dishDto);

    //删除菜品信息以及对应的口味信息
    public void deleteWithFlavor(List<Long> ids);
}
