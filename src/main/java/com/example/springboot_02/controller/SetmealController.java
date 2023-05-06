package com.example.springboot_02.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot_02.common.R;
import com.example.springboot_02.dto.DishDto;
import com.example.springboot_02.dto.SetmealDto;
import com.example.springboot_02.pojo.Category;
import com.example.springboot_02.pojo.Dish;
import com.example.springboot_02.pojo.Setmeal;
import com.example.springboot_02.service.CagetoryService;
import com.example.springboot_02.service.SetmealDishService;
import com.example.springboot_02.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * className:SetmealController
 * Package:com.example.springboot_02.controller
 * Description:一步一脚印！
 *
 * @Date: 2023/1/3 12:15
 * @Author:2692243932@qq.com
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CagetoryService cagetoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        System.out.println("id:"+id);
        SetmealDto byIdWithDish = setmealService.getByIdWithDish(id);
        return R.success(byIdWithDish);
    }

    @PostMapping
    @CacheEvict(value = "setMealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = cagetoryService.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    //删除套餐
    @DeleteMapping
    @CacheEvict(value = "setMealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }

    //操作状态
    @PostMapping("/status/{ss}")
    public R<String> updateByStatus(@PathVariable int ss,@RequestParam List<Long> ids){
        log.info("成功进入");
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(ss);
        queryWrapper.in(Setmeal::getId,ids);
        setmealService.update(setmeal,queryWrapper);
        return R.success("操作成功");
    }


    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        System.out.println(JSON.toJSON(setmealDto));
        setmealService.updateWithDish(setmealDto);
        return R.success("套餐修改成功");
    }


    @GetMapping("/list")
    @Cacheable(value = "setMealCache",key = "#setmeal.categoryId + '_' + #setmeal.status",unless = "#result == null")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
