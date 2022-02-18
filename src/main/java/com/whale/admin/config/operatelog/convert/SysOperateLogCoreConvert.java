package com.whale.admin.config.operatelog.convert;

import com.whale.framework.operatelog.core.dto.OperateLogCreateReqDTO;
import com.whale.framework.repository.model.krplus.SysOperateLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysOperateLogCoreConvert {

    SysOperateLogCoreConvert INSTANCE = Mappers.getMapper(SysOperateLogCoreConvert.class);

    SysOperateLog convert(OperateLogCreateReqDTO bean);

}
