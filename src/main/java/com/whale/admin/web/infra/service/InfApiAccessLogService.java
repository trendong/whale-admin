package com.whale.admin.web.infra.service;

import com.whale.framework.common.pojo.PageResult;
import com.whale.framework.repository.vo.infra.apiaccesslog.InfApiAccessLogExportReqVO;
import com.whale.framework.repository.vo.infra.apiaccesslog.InfApiAccessLogPageReqVO;
import com.whale.framework.repository.model.krplus.InfApiAccessLog;

import java.util.List;

/**
 * API 访问日志 Service 接口
 *
 * @author trendong
 */
public interface InfApiAccessLogService {

    /**
     * 获得 API 访问日志分页
     *
     * @param pageReqVO 分页查询
     * @return API 访问日志分页
     */
    PageResult<InfApiAccessLog> getApiAccessLogPage(InfApiAccessLogPageReqVO pageReqVO);

    /**
     * 获得 API 访问日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return API 访问日志分页
     */
    List<InfApiAccessLog> getApiAccessLogList(InfApiAccessLogExportReqVO exportReqVO);

}
