package com.example.springboot_02.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot_02.dto.SetmealDto;
import com.example.springboot_02.pojo.Setmeal;

import java.util.List;

/**
 * className:SetmealService
 * Package:com.example.springboot_02.service
 * Description:一步一脚印！
 *
 * @Date: 2023/1/1 15:04
 * @Author:2692243932@qq.com
 */
public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);

    SetmealDto getByIdWithDish(Long id);

    void updateWithDish(SetmealDto setmealDto);
}
