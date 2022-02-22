package com.whale.admin.config.apilog.service.convert;

import com.whale.admin.config.apilog.service.dto.ApiErrorLogCreateReqDTO;
import com.whale.framework.repository.model.krplus.InfApiErrorLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InfApiErrorLogCoreConvert {

    InfApiErrorLogCoreConvert INSTANCE = Mappers.getMapper(InfApiErrorLogCoreConvert.class);

    InfApiErrorLog convert(ApiErrorLogCreateReqDTO bean);

}
