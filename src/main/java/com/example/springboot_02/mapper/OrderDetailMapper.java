package com.example.springboot_02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot_02.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * className:OrderDetailMapper
 * Package:com.example.springboot_02.mapper
 * Description:一步一脚印！
 *
 * @Date: 2023/5/5 0:36
 * @Author:2692243932@qq.com
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
