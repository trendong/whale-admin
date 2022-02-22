package com.whale.admin.web.infra.convert;

import com.whale.admin.web.infra.dto.ApiErrorLogCreateReqDTO;
import com.whale.framework.repository.model.krplus.InfApiErrorLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InfApiErrorLogCoreConvert {

    InfApiErrorLogCoreConvert INSTANCE = Mappers.getMapper(InfApiErrorLogCoreConvert.class);

    InfApiErrorLog convert(ApiErrorLogCreateReqDTO bean);

}
