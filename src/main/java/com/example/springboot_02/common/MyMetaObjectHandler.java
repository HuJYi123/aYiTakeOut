package com.example.springboot_02.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * className:MyMetaObjectHandler
 * Package:com.example.springboot_02.common
 * Description:一步一脚印！
 *
 * @Date: 2022/12/30 15:43
 * @Author:2692243932@qq.com
 */


/**
 * 该类用于mybatis-plus自动填充字段
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //插入数据时填充字段
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    //更新数据时填充字段
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
