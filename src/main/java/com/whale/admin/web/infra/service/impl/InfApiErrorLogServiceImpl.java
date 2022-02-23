package com.whale.admin.web.infra.service.impl;

import com.whale.admin.config.apilog.service.enums.InfApiErrorLogProcessStatusEnum;
import com.whale.admin.web.infra.service.InfApiErrorLogService;
import com.whale.framework.common.pojo.PageResult;
import com.whale.framework.repository.vo.infra.apierrorlog.InfApiErrorLogExportReqVO;
import com.whale.framework.repository.vo.infra.apierrorlog.InfApiErrorLogPageReqVO;
import com.whale.framework.repository.mapper.krplus.InfApiErrorLogMapper;
import com.whale.framework.repository.model.krplus.InfApiErrorLog;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.whale.admin.web.infra.enums.InfErrorCodeConstants.API_ERROR_LOG_NOT_FOUND;
import static com.whale.admin.web.infra.enums.InfErrorCodeConstants.API_ERROR_LOG_PROCESSED;
import static com.whale.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * API 错误日志 Service 实现类
 *
 * @author trendong
 */
@Service
@Validated
public class InfApiErrorLogServiceImpl implements InfApiErrorLogService {

    @Resource
    private InfApiErrorLogMapper apiErrorLogMapper;

    @Override
    public PageResult<InfApiErrorLog> getApiErrorLogPage(InfApiErrorLogPageReqVO pageReqVO) {
        return apiErrorLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<InfApiErrorLog> getApiErrorLogList(InfApiErrorLogExportReqVO exportReqVO) {
        return apiErrorLogMapper.selectList(exportReqVO);
    }

    @Override
    public void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId) {
        InfApiErrorLog errorLog = apiErrorLogMapper.selectById(id);
        if (errorLog == null) {
            throw exception(API_ERROR_LOG_NOT_FOUND);
        }
        if (!InfApiErrorLogProcessStatusEnum.INIT.getStatus().equals(errorLog.getProcessStatus())) {
            throw exception(API_ERROR_LOG_PROCESSED);
        }
        // 标记处理
        apiErrorLogMapper.updateById(InfApiErrorLog.builder().id(id).processStatus(processStatus)
                .processUserId(processUserId).processTime(new Date()).build());
    }

}
