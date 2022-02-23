package com.whale.admin.web.infra.convert;

import com.whale.admin.web.infra.vo.apiaccesslog.InfApiAccessLogExcelVO;
import com.whale.admin.web.infra.vo.apiaccesslog.InfApiAccessLogRespVO;
import com.whale.framework.common.pojo.PageResult;
import com.whale.framework.repository.model.krplus.InfApiAccessLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 访问日志 Convert
 *
 * @author trendong
 */
@Mapper
public interface InfApiAccessLogConvert {

    InfApiAccessLogConvert INSTANCE = Mappers.getMapper(InfApiAccessLogConvert.class);

    InfApiAccessLogRespVO convert(InfApiAccessLog bean);

    List<InfApiAccessLogRespVO> convertList(List<InfApiAccessLog> list);

    PageResult<InfApiAccessLogRespVO> convertPage(PageResult<InfApiAccessLog> page);

    List<InfApiAccessLogExcelVO> convertList02(List<InfApiAccessLog> list);

}
