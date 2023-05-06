package com.example.springboot_02.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springboot_02.common.BaseContext;
import com.example.springboot_02.common.R;
import com.example.springboot_02.pojo.Employee;
import com.example.springboot_02.pojo.User;
import com.example.springboot_02.service.UserService;
import com.example.springboot_02.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * className:UserController
 * Package:com.example.springboot_02.controller
 * Description:一步一脚印！
 *
 * @Date: 2023/5/1 14:51
 * @Author:2692243932@qq.com
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if (StringUtils.isNotBlank(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            System.out.println("code:"+code);
            //SMSUtils.sendMessage("阿奕外卖","",phone,code);
            session.setAttribute(phone,code);
            return R.success("手机验证码发送成功");
        }
        return R.error("手机验证码发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        String code1 = (String) map.get("code");
        String phone = (String)map.get("phone");
        String code2 = (String) session.getAttribute(phone);
        if(code2 != null && code1.equals(code2)){

            //判断当前用户是否为新用户，是新用户则进行自动注册
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("验证码错误");
    }

    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session){
        session.removeAttribute("user");
        return R.success("退出成功");
    }
}
