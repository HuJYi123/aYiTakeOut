package com.example.springboot_02.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot_02.common.R;
import com.example.springboot_02.pojo.Category;
import com.example.springboot_02.service.CagetoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * className:CategoryController
 * Package:com.example.springboot_02.controller
 * Description:一步一脚印！
 *
 * @Date: 2022/12/30 17:07
 * @Author:2692243932@qq.com
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CagetoryService cagetoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        cagetoryService.save(category);
        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        cagetoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 当分类关联了菜品或套餐时，分类不允许删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
//        cagetoryService.removeById(id);
        cagetoryService.remove(ids);
        return R.success("分类信息删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        cagetoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = cagetoryService.list(queryWrapper);
        return R.success(list);
    }
}
