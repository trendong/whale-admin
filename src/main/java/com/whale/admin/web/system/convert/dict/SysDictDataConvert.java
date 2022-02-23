package com.whale.admin.web.system.convert.dict;

import com.whale.admin.web.system.vo.dict.data.*;
import com.whale.framework.common.pojo.PageResult;
import com.whale.framework.repository.model.krplus.SysDictData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
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

}
