package com.whale.admin.excel.service;

import com.whale.framework.repository.model.krplus.SysDictData;

import java.util.List;

/**
 * 字典数据 Service 接口
 *
 * @author 芋道源码
 */
public interface ISysDictDataCoreService extends DictDataFrameworkService {

    /**
     * 初始化字典数据的本地缓存
     */
    void initLocalCache();

    List<SysDictData> selectList();

}
