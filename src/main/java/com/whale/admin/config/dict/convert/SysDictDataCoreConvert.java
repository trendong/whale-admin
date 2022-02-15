package com.whale.admin.config.dict.convert;

import com.whale.framework.dict.dto.DictDataRespDTO;
import com.whale.framework.repository.model.krplus.SysDictData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface SysDictDataCoreConvert {

    SysDictDataCoreConvert INSTANCE = Mappers.getMapper(SysDictDataCoreConvert.class);

    DictDataRespDTO convert02(SysDictData bean);

    List<DictDataRespDTO> convertList03(Collection<SysDictData> list);

}
