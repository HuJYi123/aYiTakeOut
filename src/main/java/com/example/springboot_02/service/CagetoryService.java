package com.example.springboot_02.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot_02.pojo.Category;

/**
 * className:CagetoryService
 * Package:com.example.springboot_02.service
 * Description:一步一脚印！
 *
 * @Date: 2022/12/30 17:02
 * @Author:2692243932@qq.com
 */
public interface CagetoryService extends IService<Category> {

    public void remove(Long id);
}
