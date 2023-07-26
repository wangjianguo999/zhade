package com.jeesite.modules.api;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class RespFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String powered = response.getHeader("powered-by");
        if(powered != null && powered.equals("JeeSite V4.3.1 0")){
            response.setHeader("powered-by","");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    /**
     * filter创建后会保存在内存中，当web应用移除或者服务器停止时才销毁，该方法在Filter的生命周期中仅执行一次，在这个方法中，可以释放过滤器使用的资源
     */
    @Override
    public void destroy() {

    }

}
