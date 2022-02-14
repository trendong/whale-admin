package com.whale.admin.web.system.convert.dict;

import com.whale.admin.excel.dto.DictDataRespDTO;
import com.whale.admin.web.system.vo.dict.data.*;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.model.krplus.SysDictData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface SysDictDataConvert {

    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    List<SysDictDataSimpleRespVO> convertList(List<SysDictData> list);

    SysDictDataRespVO convert(SysDictData bean);

    PageResult<SysDictDataRespVO> convertPage(PageResult<SysDictData> page);

    SysDictData convert(SysDictDataUpdateReqVO bean);

    SysDictData convert(SysDictDataCreateReqVO bean);

    List<SysDictDataExcelVO> convertList02(List<SysDictData> bean);

    DictDataRespDTO convert02(SysDictData bean);

    List<DictDataRespDTO> convertList03(Collection<SysDictData> list);

}