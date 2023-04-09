package com.quvideo.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class HttpServletRequestReplacedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        // 只处理POST和PUT请求
        if (servletRequest instanceof HttpServletRequest request) {
            String requestMethod = request.getMethod();
            if (requestMethod.equals("POST") || requestMethod.equals("PUT")) {
                requestWrapper = new ReHttpServletRequestWrapper(request);
            }
        }
        // 在chain.doFilter中传递新的request对象
        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
