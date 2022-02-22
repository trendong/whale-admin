package com.whale.admin.config.apilog.service.impl;

import com.whale.admin.config.apilog.service.InfApiErrorLogCoreService;
import com.whale.admin.config.apilog.service.convert.InfApiErrorLogCoreConvert;
import com.whale.admin.config.apilog.service.dto.ApiErrorLogCreateReqDTO;
import com.whale.admin.config.apilog.service.enums.InfApiErrorLogProcessStatusEnum;
import com.whale.framework.repository.mapper.krplus.InfApiErrorLogMapper;
import com.whale.framework.repository.model.krplus.InfApiErrorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * API 错误日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class InfApiErrorLogCoreServiceImpl implements InfApiErrorLogCoreService {

    @Resource
    private InfApiErrorLogMapper infApiErrorLogMapper;

    @Override
    @Async
    public void createApiErrorLogAsync(ApiErrorLogCreateReqDTO createDTO) {
        InfApiErrorLog apiErrorLog = InfApiErrorLogCoreConvert.INSTANCE.convert(createDTO);
        apiErrorLog.setProcessStatus(InfApiErrorLogProcessStatusEnum.INIT.getStatus());
        infApiErrorLogMapper.insert(apiErrorLog);
    }

}
