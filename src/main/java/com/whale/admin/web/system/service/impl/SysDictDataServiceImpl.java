package com.whale.admin.web.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.common.annotations.VisibleForTesting;
import com.whale.admin.web.system.convert.dict.SysDictDataConvert;
import com.whale.admin.web.system.service.ISysDictDataService;
import com.whale.admin.web.system.service.ISysDictTypeService;
import com.whale.admin.web.system.vo.dict.data.SysDictDataCreateReqVO;
import com.whale.admin.web.system.vo.dict.data.SysDictDataUpdateReqVO;
import com.whale.framework.common.enums.CommonStatusEnum;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.system.dict.SysDictDataExportReqVO;
import com.whale.framework.repository.common.vo.system.dict.SysDictDataPageReqVO;
import com.whale.framework.repository.mapper.krplus.SysDictDataMapper;
import com.whale.framework.repository.model.krplus.SysDictData;
import com.whale.framework.repository.model.krplus.SysDictType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.whale.admin.web.system.enums.system.SysErrorCodeConstants.*;
import static com.whale.framework.repository.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 字典数据 Service 实现类
 *
 * @author trendong
 */
@Service
@DS("slave_2")
public class SysDictDataServiceImpl implements ISysDictDataService {

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<SysDictData> COMPARATOR_TYPE_AND_SORT = Comparator
            .comparing(SysDictData::getDictType)
            .thenComparingInt(SysDictData::getSort);

    @Resource
    private ISysDictTypeService iSysDictTypeService;

    @Resource
    private SysDictDataMapper dictDataMapper;

//    @Resource
//    private SysDictDataProducer dictDataProducer;

    /**
     * 如果字典数据发生变化，从数据库中获取最新的全量字典数据。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前字典数据的最大更新时间
     * @return 字典数据列表
     */
    private List<SysDictData> loadDictDataIfUpdate(Date maxUpdateTime) {
        return dictDataMapper.selectList();
    }

    @Override
    public List<SysDictData> getDictDatas() {
        List<SysDictData> list = dictDataMapper.selectList();
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public PageResult<SysDictData> getDictDataPage(SysDictDataPageReqVO reqVO) {
        return dictDataMapper.selectPage(reqVO);
    }

    @Override
    public List<SysDictData> getDictDatas(SysDictDataExportReqVO reqVO) {
        List<SysDictData> list = dictDataMapper.selectList(reqVO);
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public SysDictData getDictData(Long id) {
        return dictDataMapper.selectById(id);
    }

    @Override
    public Long createDictData(SysDictDataCreateReqVO reqVO) {
        // 校验正确性
        this.checkCreateOrUpdate(null, reqVO.getValue(), reqVO.getDictType());
        // 插入字典类型
        SysDictData dictData = SysDictDataConvert.INSTANCE.convert(reqVO);
        dictDataMapper.insert(dictData);
        // 发送刷新消息
//        dictDataProducer.sendDictDataRefreshMessage();
        return dictData.getId();
    }

    @Override
    public void updateDictData(SysDictDataUpdateReqVO reqVO) {
        // 校验正确性
        this.checkCreateOrUpdate(reqVO.getId(), reqVO.getValue(), reqVO.getDictType());
        // 更新字典类型
        SysDictData updateObj = SysDictDataConvert.INSTANCE.convert(reqVO);
        dictDataMapper.updateById(updateObj);
        // 发送刷新消息
//        dictDataProducer.sendDictDataRefreshMessage();
    }

    @Override
    public void deleteDictData(Long id) {
        // 校验是否存在
        this.checkDictDataExists(id);
        // 删除字典数据
        dictDataMapper.deleteById(id);
        // 发送刷新消息
//        dictDataProducer.sendDictDataRefreshMessage();
    }

    @Override
    public Long countByDictType(String dictType) {
        return dictDataMapper.selectCountByDictType(dictType);
    }

    private void checkCreateOrUpdate(Long id, String value, String dictType) {
        // 校验自己存在
        checkDictDataExists(id);
        // 校验字典类型有效
        checkDictTypeValid(dictType);
        // 校验字典数据的值的唯一性
        checkDictDataValueUnique(id, dictType, value);
    }

    @VisibleForTesting
    public void checkDictDataValueUnique(Long id, String dictType, String value) {
        SysDictData dictData = dictDataMapper.selectByDictTypeAndValue(dictType, value);
        if (dictData == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
        if (!dictData.getId().equals(id)) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
    }

    @VisibleForTesting
    public void checkDictDataExists(Long id) {
        if (id == null) {
            return;
        }
        SysDictData dictData = dictDataMapper.selectById(id);
        if (dictData == null) {
            throw exception(DICT_DATA_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    public void checkDictTypeValid(String type) {
        SysDictType dictType = iSysDictTypeService.getDictType(type);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus())) {
            throw exception(DICT_TYPE_NOT_ENABLE);
        }
    }

}
