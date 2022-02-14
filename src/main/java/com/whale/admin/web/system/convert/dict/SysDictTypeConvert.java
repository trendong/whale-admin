package com.whale.admin.web.system.convert.dict;

import com.whale.admin.web.system.vo.dict.type.*;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.model.krplus.SysDictType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysDictTypeConvert {

    SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

    PageResult<SysDictTypeRespVO> convertPage(PageResult<SysDictType> bean);

    SysDictTypeRespVO convert(SysDictType bean);

    SysDictType convert(SysDictTypeCreateReqVO bean);

    SysDictType convert(SysDictTypeUpdateReqVO bean);

    List<SysDictTypeSimpleRespVO> convertList(List<SysDictType> list);

    List<SysDictTypeExcelVO> convertList02(List<SysDictType> list);

}
