package com.whale.admin.web.infra.service.impl;

import com.whale.admin.web.infra.service.InfApiAccessLogService;
import com.whale.framework.common.pojo.PageResult;
import com.whale.framework.repository.vo.infra.apiaccesslog.InfApiAccessLogExportReqVO;
import com.whale.framework.repository.vo.infra.apiaccesslog.InfApiAccessLogPageReqVO;
import com.whale.framework.repository.mapper.krplus.InfApiAccessLogMapper;
import com.whale.framework.repository.model.krplus.InfApiAccessLog;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * API 访问日志 Service 实现类
 *
 * @author trendong
 */
@Service
@Validated
public class InfApiAccessLogServiceImpl implements InfApiAccessLogService {

    @Resource
    private InfApiAccessLogMapper apiAccessLogMapper;

    @Override
    public PageResult<InfApiAccessLog> getApiAccessLogPage(InfApiAccessLogPageReqVO pageReqVO) {
        return apiAccessLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<InfApiAccessLog> getApiAccessLogList(InfApiAccessLogExportReqVO exportReqVO) {
        return apiAccessLogMapper.selectList(exportReqVO);
    }

}
