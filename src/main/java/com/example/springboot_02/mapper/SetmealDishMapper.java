package com.example.springboot_02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot_02.pojo.Setmeal;
import com.example.springboot_02.pojo.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

/**
 * className:SetmealMapper
 * Package:com.example.springboot_02.mapper
 * Description:一步一脚印！
 *
 * @Date: 2023/1/1 15:02
 * @Author:2692243932@qq.com
 */

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
}
