package com.wereash.restaurant.filter;

import com.alibaba.fastjson.JSON;
import com.wereash.restaurant.common.BaseContext;
import com.wereash.restaurant.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

//    路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
//      1.获取本次请求的URI
        String requestURI=request.getRequestURI();
        log.info("拦截到请求：{}",requestURI);
//        直接放行的URL,不需要处理的请求路径
        String urls[]=new String[]{
                "/employee/login",
                "/employee/logout",
//                页面请求随便看，数据不能看
                "/backend/**",
                "/front/**"
        };
//        2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        if(check){
            log.info("本次{}请求不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));

            Long empId=(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
/*            long id = Thread.currentThread().getId();
            log.info("线程id为：{}",id);*/

            filterChain.doFilter(request,response);
            return;
        }

//        如果未登录则返回未登录结果，通过输出输入流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("用户未登录");
        return;
    }
    public boolean check(String[] urls,String requestURI){
        for(String url:urls){
            boolean match=PATH_MATCHER.match(url,requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
