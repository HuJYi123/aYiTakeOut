package com.example.springboot_02.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot_02.common.R;
import com.example.springboot_02.dto.DishDto;
import com.example.springboot_02.pojo.Category;
import com.example.springboot_02.pojo.Dish;
import com.example.springboot_02.pojo.DishFlavor;
import com.example.springboot_02.service.CagetoryService;
import com.example.springboot_02.service.DishFlavorService;
import com.example.springboot_02.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * className:DishController
 * Package:com.example.springboot_02.controller
 * Description:一步一脚印！
 *
 * @Date: 2023/1/1 22:20
 * @Author:2692243932@qq.com
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DishService dishService;

    @Autowired
    private CagetoryService cagetoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        //清理菜品中所有缓存
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        //清理菜品中单个缓存
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //创建分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(name != null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = cagetoryService.getById(categoryId);
            if (category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);
        return R.success(byIdWithFlavor);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        //清理菜品中所有缓存
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        //清理菜品中单个缓存
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("修改菜品成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dishService.deleteWithFlavor(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{ss}")
    public R<String> updateByStatus(@PathVariable int ss,@RequestParam List<Long> ids){
        log.info("成功进入");
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        Dish dish = new Dish();
        dish.setStatus(ss);
        queryWrapper.in(Dish::getId,ids);
        dishService.update(dish,queryWrapper);
        return R.success("操作成功");
    }

    /*@GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }*/

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        List<DishDto> dishDtoList = new ArrayList<>();
        //设置key
        String key = "dish_"+dish.getCategoryId() + "_" + dish.getStatus();
        //先从redis获取缓存数据
        Set<String> members = stringRedisTemplate.opsForSet().members(key);
        Iterator<String> iterator = members.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            dishDtoList.add(JSONUtil.toBean(next,DishDto.class));
        }

//        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
//        String o = (String) redisTemplate.opsForValue().get(key);
        if (!dishDtoList.isEmpty()){
//            DishDto dishDto = JSON.parseObject
//            System.out.println("8888");
////            System.out.println(dishDto);
            return R.success(dishDtoList);
        }
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        dishDtoList = list.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = cagetoryService.getById(categoryId);
            if (category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,id);
            List<DishFlavor> list1 = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());

        //如果redis不存在缓存，则添加进去
//        String s = JSON.toJSONString(dishDtoList);
//        System.out.println("输出"+dishDtoList);
//        System.out.println("输出1"+ s);
//        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);
        for(DishDto dishDto: dishDtoList){
            stringRedisTemplate.opsForSet().add(key,JSONUtil.toJsonStr(dishDto));
            stringRedisTemplate.expire(key,60,TimeUnit.MINUTES);
        }
        return R.success(dishDtoList);
    }
}
