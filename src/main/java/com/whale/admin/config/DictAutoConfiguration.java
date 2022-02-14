package com.whale.admin.config;

import com.whale.admin.excel.service.DictDataFrameworkService;
import com.whale.admin.excel.util.DictFrameworkUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DictAutoConfiguration {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public DictFrameworkUtils dictUtils(DictDataFrameworkService dictDataFrameworkService) {
        DictFrameworkUtils.init(dictDataFrameworkService);
        return new DictFrameworkUtils();
    }

}
