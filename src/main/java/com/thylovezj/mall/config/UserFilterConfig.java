package com.thylovezj.mall.config;

import com.thylovezj.mall.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：     User过滤器的配置
 */
@Configuration
public class UserFilterConfig {

    @Bean
    public UserFilter UserFilter() {
        return new UserFilter();
    }

    @Bean(name = "UserFilterConf")
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(UserFilter());
        filterRegistrationBean.addUrlPatterns("/cart/*");
        filterRegistrationBean.addUrlPatterns("/order/*");
        filterRegistrationBean.setName("UserFilterConf");
        return filterRegistrationBean;
    }
}

