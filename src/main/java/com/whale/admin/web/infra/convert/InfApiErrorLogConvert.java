package com.whale.admin.web.infra.convert;

import com.whale.admin.web.infra.vo.apierrorlog.InfApiErrorLogExcelVO;
import com.whale.admin.web.infra.vo.apierrorlog.InfApiErrorLogRespVO;
import com.whale.framework.common.pojo.PageResult;
import com.whale.framework.repository.model.krplus.InfApiErrorLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 错误日志 Convert
 *
 * @author trendong
 */
@Mapper
public interface InfApiErrorLogConvert {

    InfApiErrorLogConvert INSTANCE = Mappers.getMapper(InfApiErrorLogConvert.class);

    InfApiErrorLogRespVO convert(InfApiErrorLog bean);

    PageResult<InfApiErrorLogRespVO> convertPage(PageResult<InfApiErrorLog> page);

    List<InfApiErrorLogExcelVO> convertList02(List<InfApiErrorLog> list);

}
