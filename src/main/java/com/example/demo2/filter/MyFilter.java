package com.example.demo2.filter;



import com.example.demo2.pojo.User;
import com.example.demo2.utils.ConstantValue;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "myFilter",urlPatterns = {"/*"},
    initParams = {@WebInitParam(name = "login",value = "/login"),
            @WebInitParam(name = "register",value = "/register"),
            @WebInitParam(name = "manager",value = "/manager"),
            @WebInitParam(name = "encoding",value = "utf-8"),})
public class MyFilter implements Filter {

    private FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig=filterConfig;
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String login=filterConfig.getInitParameter("login");
        String register=filterConfig.getInitParameter("register");
        String encoding=filterConfig.getInitParameter("encoding");
        String manager=filterConfig.getInitParameter("manager");

        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        HttpSession session=request.getSession();

        request.setCharacterEncoding(encoding);
        String uri=request.getRequestURI();
        User current_user=(User)session.getAttribute(ConstantValue.CURRENT_USER);
        if (uri.contains(login) || uri.contains(register)){
            filterChain.doFilter(request,response);
        }else {
            if (current_user == null){
                request.getRequestDispatcher("/user/login").forward(request,response);
            }else {
                if (uri.contains(manager)){
                    if (ConstantValue.POSITION_MANAGER != current_user.getPosition()){
                        request.getRequestDispatcher("/user/login").forward(request,response);
                    }else {
                        filterChain.doFilter(request,response);
                    }
                }else {
                    filterChain.doFilter(request,response);
                }
            }
        }

        /*String cookie_user_name=null;
        Cookie[] cookie=request.getCookies();
        if(cookie!=null) {
            for(Cookie c:cookie) {
                if(c.getName().equals("cookie_user_name")) {
                    cookie_user_name=c.getValue();
                }
            }
        }
        session.setAttribute("cookie_user_name", cookie_user_name);
        System.out.println("doFilter");
        filterChain.doFilter(request,response);*/
    }

    @Override
    public void destroy() {
        filterConfig=null;
        System.out.println("destroy");
    }
}
