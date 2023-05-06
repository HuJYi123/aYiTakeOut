package com.example.springboot_02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot_02.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * className:ShoppingCartMapper
 * Package:com.example.springboot_02.mapper
 * Description:一步一脚印！
 *
 * @Date: 2023/5/1 18:19
 * @Author:2692243932@qq.com
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
