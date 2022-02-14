package com.whale.admin.web.system.service;


import com.whale.admin.web.system.vo.dict.type.SysDictTypeCreateReqVO;
import com.whale.admin.web.system.vo.dict.type.SysDictTypeUpdateReqVO;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.system.dict.SysDictTypeExportReqVO;
import com.whale.framework.repository.common.vo.system.dict.SysDictTypePageReqVO;
import com.whale.framework.repository.model.krplus.SysDictType;

import java.util.List;

/**
 * 字典类型 Service 接口
 *
 * @author trendong
 */
public interface ISysDictTypeService {

    /**
     * 创建字典类型
     *
     * @param reqVO 字典类型信息
     * @return 字典类型编号
     */
    Long createDictType(SysDictTypeCreateReqVO reqVO);

    /**
     * 更新字典类型
     *
     * @param reqVO 字典类型信息
     */
    void updateDictType(SysDictTypeUpdateReqVO reqVO);

    /**
     * 删除字典类型
     *
     * @param id 字典类型编号
     */
    void deleteDictType(Long id);

    /**
     * 获得字典类型分页列表
     *
     * @param reqVO 分页请求
     * @return 字典类型分页列表
     */
    PageResult<SysDictType> getDictTypePage(SysDictTypePageReqVO reqVO);

    /**
     * 获得字典类型列表
     *
     * @param reqVO 列表请求
     * @return 字典类型列表
     */
    List<SysDictType> getDictTypeList(SysDictTypeExportReqVO reqVO);

    /**
     * 获得字典类型详情
     *
     * @param id 字典类型编号
     * @return 字典类型
     */
    SysDictType getDictType(Long id);

    /**
     * 获得字典类型详情
     *
     * @param type 字典类型
     * @return 字典类型详情
     */
    SysDictType getDictType(String type);

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型列表
     */
    List<SysDictType> getDictTypeList();

}
