package com.thylovezj.mall.filter;

import com.thylovezj.mall.common.Constant;
import com.thylovezj.mall.model.pojo.User;
import com.thylovezj.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserFilter implements Filter {
    public static User user;
    @Autowired
    UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();

        user = (User) session.getAttribute(Constant.THYLOVEZJ_MALL_USER);
        if (user == null) {
            PrintWriter writer = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            writer.write("{\n" +
                    "    \"status\": 10007,\n" +
                    "    \"message\": \"NEED_LOGIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            writer.flush();
            writer.close();
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
