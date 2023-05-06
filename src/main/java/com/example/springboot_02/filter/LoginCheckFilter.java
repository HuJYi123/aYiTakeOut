package com.example.springboot_02.filter;

//import com.alibaba.fastjson.JSON;
//import com.example.springboot_02.common.BaseContext;
import com.alibaba.fastjson.JSON;
import com.example.springboot_02.common.BaseContext;
import com.example.springboot_02.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * className:LoginCheckFilter
 * Package:com.example.springboot_02.filter
 * Description:一步一脚印！
 *
 * @Date: 2022/12/29 23:50
 * @Author:2692243932@qq.com
 */

/**
 * 过滤器，所有访问路径都经过这里
 */

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
//        定义放行
        String [] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //不需要处理
        if (check){
            filterChain.doFilter(request,response);
            return;
        }
        //需要处理,已登陆状态
        if(request.getSession().getAttribute("employee") != null){
            Long id = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request,response);
            return;
        }

        //判断客户端登录状态
        if(request.getSession().getAttribute("user") != null){
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }
//        未登录状态
        response.getWriter().write(JSON.toJSONString((R.error("NOTLOGIN"))));
        return;
    }

    public boolean check(String [] urls,String requestURI){
        for(String url : urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
