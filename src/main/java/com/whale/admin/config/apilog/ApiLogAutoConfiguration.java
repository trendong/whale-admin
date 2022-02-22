package com.whale.admin.config.apilog;

import com.whale.admin.config.WebAutoConfiguration;
import com.whale.admin.filter.ApiAccessLogFilter;
import com.whale.admin.web.infra.service.InfApiAccessLogService;
import com.whale.framework.common.enums.WebFilterOrderEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
@AutoConfigureAfter(WebAutoConfiguration.class)
public class ApiLogAutoConfiguration {

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(@Value("${spring.application.name}") String applicationName,
                                                                         InfApiAccessLogService infApiAccessLogService) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(applicationName, infApiAccessLogService);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

}
