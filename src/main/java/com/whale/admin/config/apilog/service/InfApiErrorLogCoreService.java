package com.whale.admin.config.apilog.service;


import com.whale.admin.config.apilog.service.dto.ApiErrorLogCreateReqDTO;

import javax.validation.Valid;

/**
 * API 错误日志 Service 接口
 *
 * @author trendong
 */
public interface InfApiErrorLogCoreService {

    /**
     * 创建 API 错误日志
     *
     * @param createDTO 创建信息
     */
    void createApiErrorLogAsync(@Valid ApiErrorLogCreateReqDTO createDTO);

}
