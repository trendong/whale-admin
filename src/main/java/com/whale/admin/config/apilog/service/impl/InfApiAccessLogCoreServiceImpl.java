package com.whale.admin.config.apilog.service.impl;

import com.whale.admin.config.apilog.service.dto.ApiAccessLogCreateReqDTO;
import com.whale.admin.config.apilog.service.convert.InfApiAccessLogCoreConvert;
import com.whale.admin.config.apilog.service.InfApiAccessLogCoreService;
import com.whale.framework.repository.mapper.krplus.InfApiAccessLogMapper;
import com.whale.framework.repository.model.krplus.InfApiAccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * API 访问日志 Service 实现类
 *
 * @author trendong
 */
@Service
@Validated
public class InfApiAccessLogCoreServiceImpl implements InfApiAccessLogCoreService {

    @Resource
    private InfApiAccessLogMapper infApiAccessLogMapper;

    @Override
    @Async
    public void createApiAccessLogAsync(ApiAccessLogCreateReqDTO createDTO) {
        InfApiAccessLog apiAccessLog = InfApiAccessLogCoreConvert.INSTANCE.convert(createDTO);
        infApiAccessLogMapper.insert(apiAccessLog);
    }

}
