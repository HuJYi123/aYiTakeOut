package com.example.springboot_02.common;

/**
 * className:BaseContext
 * Package:com.example.springboot_02.common
 * Description:一步一脚印！
 *
 * @Date: 2023/3/17 17:51
 * @Author:2692243932@qq.com
 */


/**
 * 用于保存和获取当前登录用户ID
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
