package com.whale.admin.web.system.service;

import com.whale.admin.excel.service.DictDataFrameworkService;

/**
 * 字典数据 Service 接口
 *
 * @author trendong
 */
public interface ISysDictDataCoreService extends DictDataFrameworkService {

    /**
     * 初始化字典数据的本地缓存
     */
    void initLocalCache();

}
