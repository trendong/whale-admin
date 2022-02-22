package com.whale.admin.web.infra.service;

import com.whale.admin.web.infra.dto.ApiErrorLogCreateReqDTO;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.infra.apierrorlog.InfApiErrorLogExportReqVO;
import com.whale.framework.repository.common.vo.infra.apierrorlog.InfApiErrorLogPageReqVO;
import com.whale.framework.repository.model.krplus.InfApiErrorLog;

import java.util.List;

/**
 * API 错误日志 Service 接口
 *
 * @author trendong
 */
public interface InfApiErrorLogService {

    /**
     * 获得 API 错误日志分页
     *
     * @param pageReqVO 分页查询
     * @return API 错误日志分页
     */
    PageResult<InfApiErrorLog> getApiErrorLogPage(InfApiErrorLogPageReqVO pageReqVO);

    /**
     * 获得 API 错误日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return API 错误日志分页
     */
    List<InfApiErrorLog> getApiErrorLogList(InfApiErrorLogExportReqVO exportReqVO);

    /**
     * 更新 API 错误日志已处理
     *
     * @param id API 日志编号
     * @param processStatus 处理结果
     * @param processUserId 处理人
     */
    void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId);

    void createApiErrorLogAsync(ApiErrorLogCreateReqDTO errorLog);
}
