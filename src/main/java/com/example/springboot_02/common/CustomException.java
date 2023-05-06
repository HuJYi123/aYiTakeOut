package com.example.springboot_02.common;

/**
 * className:CustomException
 * Package:com.example.springboot_02.common
 * Description:一步一脚印！
 *
 * @Date: 2023/1/1 15:38
 * @Author:2692243932@qq.com
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
