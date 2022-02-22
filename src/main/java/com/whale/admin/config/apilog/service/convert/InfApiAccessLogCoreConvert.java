package com.whale.admin.config.apilog.service.convert;

import com.whale.admin.config.apilog.service.dto.ApiAccessLogCreateReqDTO;
import com.whale.framework.repository.model.krplus.InfApiAccessLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InfApiAccessLogCoreConvert {

    InfApiAccessLogCoreConvert INSTANCE = Mappers.getMapper(InfApiAccessLogCoreConvert.class);

    InfApiAccessLog convert(ApiAccessLogCreateReqDTO bean);

}
