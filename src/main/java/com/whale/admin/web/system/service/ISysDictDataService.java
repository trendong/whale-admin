package com.whale.admin.web.system.service;

import com.whale.admin.web.system.vo.dict.data.SysDictDataCreateReqVO;
import com.whale.admin.web.system.vo.dict.data.SysDictDataUpdateReqVO;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.system.dict.SysDictDataExportReqVO;
import com.whale.framework.repository.common.vo.system.dict.SysDictDataPageReqVO;
import com.whale.framework.repository.model.krplus.SysDictData;

import java.util.List;

/**
 * 字典数据 Service 接口
 *
 * @author trendong
 */
public interface ISysDictDataService {

    /**
     * 创建字典数据
     *
     * @param reqVO 字典数据信息
     * @return 字典数据编号
     */
    Long createDictData(SysDictDataCreateReqVO reqVO);

    /**
     * 更新字典数据
     *
     * @param reqVO 字典数据信息
     */
    void updateDictData(SysDictDataUpdateReqVO reqVO);

    /**
     * 删除字典数据
     *
     * @param id 字典数据编号
     */
    void deleteDictData(Long id);

    /**
     * 获得字典数据列表
     *
     * @return 字典数据全列表
     */
    List<SysDictData> getDictDatas();

    /**
     * 获得字典数据分页列表
     *
     * @param reqVO 分页请求
     * @return 字典数据分页列表
     */
    PageResult<SysDictData> getDictDataPage(SysDictDataPageReqVO reqVO);

    /**
     * 获得字典数据列表
     *
     * @param reqVO 列表请求
     * @return 字典数据列表
     */
    List<SysDictData> getDictDatas(SysDictDataExportReqVO reqVO);

    /**
     * 获得字典数据详情
     *
     * @param id 字典数据编号
     * @return 字典数据
     */
    SysDictData getDictData(Long id);

    /**
     * 获得指定字典类型的数据数量
     *
     * @param dictType 字典类型
     * @return 数据数量
     */
    Long countByDictType(String dictType);

}
