package com.whale.admin.web.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.common.annotations.VisibleForTesting;
import com.whale.admin.web.system.convert.dict.SysDictTypeConvert;
import com.whale.admin.web.system.service.ISysDictDataService;
import com.whale.admin.web.system.service.ISysDictTypeService;
import com.whale.admin.web.system.vo.dict.type.SysDictTypeCreateReqVO;
import com.whale.admin.web.system.vo.dict.type.SysDictTypeUpdateReqVO;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.system.dict.SysDictTypeExportReqVO;
import com.whale.framework.repository.common.vo.system.dict.SysDictTypePageReqVO;
import com.whale.framework.repository.mapper.krplus.SysDictTypeMapper;
import com.whale.framework.repository.model.krplus.SysDictType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.whale.admin.web.system.enums.SysErrorCodeConstants.*;
import static com.whale.framework.repository.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 字典类型 Service 实现类
 *
 * @author trendong
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {

    @Resource
    private ISysDictDataService dictDataService;

    @Resource
    private SysDictTypeMapper dictTypeMapper;

    @Override
    public PageResult<SysDictType> getDictTypePage(SysDictTypePageReqVO reqVO) {
        return dictTypeMapper.selectPage(reqVO);
    }

    @Override
    public List<SysDictType> getDictTypeList(SysDictTypeExportReqVO reqVO) {
        return dictTypeMapper.selectList(reqVO);
    }

    @Override
    public SysDictType getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public SysDictType getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }

    @Override
    public Long createDictType(SysDictTypeCreateReqVO reqVO) {
        // 校验正确性
        this.checkCreateOrUpdate(null, reqVO.getName(), reqVO.getType());
        // 插入字典类型
        SysDictType dictType = SysDictTypeConvert.INSTANCE.convert(reqVO);
        dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    public void updateDictType(SysDictTypeUpdateReqVO reqVO) {
        // 校验正确性
        this.checkCreateOrUpdate(reqVO.getId(), reqVO.getName(), null);
        // 更新字典类型
        SysDictType updateObj = SysDictTypeConvert.INSTANCE.convert(reqVO);
        dictTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictType(Long id) {
        // 校验是否存在
        SysDictType dictType = this.checkDictTypeExists(id);
        // 校验是否有字典数据
        if (dictDataService.countByDictType(dictType.getType()) > 0) {
            throw exception(DICT_TYPE_HAS_CHILDREN);
        }
        // 删除字典类型
        dictTypeMapper.deleteById(id);
    }

    @Override
    public List<SysDictType> getDictTypeList() {
        return dictTypeMapper.selectList();
    }

    private void checkCreateOrUpdate(Long id, String name, String type) {
        // 校验自己存在
        checkDictTypeExists(id);
        // 校验字典类型的名字的唯一性
        checkDictTypeNameUnique(id, name);
        // 校验字典类型的类型的唯一性
        checkDictTypeUnique(id, type);
    }

    @VisibleForTesting
    public void checkDictTypeNameUnique(Long id, String name) {
        SysDictType dictType = dictTypeMapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
    }

    @VisibleForTesting
    public void checkDictTypeUnique(Long id, String type) {
        SysDictType dictType = dictTypeMapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    @VisibleForTesting
    public SysDictType checkDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        SysDictType dictType = dictTypeMapper.selectById(id);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }

}
