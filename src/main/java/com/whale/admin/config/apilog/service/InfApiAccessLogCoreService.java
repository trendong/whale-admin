package com.whale.admin.config.apilog.service;


import com.whale.admin.config.apilog.service.dto.ApiAccessLogCreateReqDTO;

import javax.validation.Valid;

/**
 * API 访问日志 Service 接口
 *
 * @author trendong
 */
public interface InfApiAccessLogCoreService {

    /**
     * 创建 API 访问日志
     *
     * @param createDTO 创建信息
     */
    void createApiAccessLogAsync(@Valid ApiAccessLogCreateReqDTO createDTO);

}
