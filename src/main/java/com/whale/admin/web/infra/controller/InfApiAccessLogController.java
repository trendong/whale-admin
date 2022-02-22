package com.whale.admin.web.infra.controller;

import com.whale.admin.common.util.ExcelUtils;
import com.whale.admin.web.infra.convert.InfApiAccessLogConvert;
import com.whale.admin.web.infra.service.InfApiAccessLogService;
import com.whale.admin.web.infra.vo.apiaccesslog.InfApiAccessLogExcelVO;
import com.whale.admin.web.infra.vo.apiaccesslog.InfApiAccessLogRespVO;
import com.whale.framework.common.pojo.CommonResult;
import com.whale.framework.operatelog.core.annotations.OperateLog;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.infra.apiaccesslog.InfApiAccessLogExportReqVO;
import com.whale.framework.repository.common.vo.infra.apiaccesslog.InfApiAccessLogPageReqVO;
import com.whale.framework.repository.model.krplus.InfApiAccessLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.whale.framework.common.pojo.CommonResult.success;
import static com.whale.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "API 访问日志")
@RestController
@RequestMapping("/infra/api-access-log")
@Validated
public class InfApiAccessLogController {

    @Resource
    private InfApiAccessLogService apiAccessLogService;

    @GetMapping("/page")
    @ApiOperation("获得API 访问日志分页")
//    @PreAuthorize("@ss.hasPermission('infra:api-access-log:query')")
    public CommonResult<PageResult<InfApiAccessLogRespVO>> getApiAccessLogPage(@Valid InfApiAccessLogPageReqVO pageVO) {
        PageResult<InfApiAccessLog> pageResult = apiAccessLogService.getApiAccessLogPage(pageVO);
        return success(InfApiAccessLogConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出API 访问日志 Excel")
//    @PreAuthorize("@ss.hasPermission('infra:api-access-log:export')")
    @OperateLog(type = EXPORT)
    public void exportApiAccessLogExcel(@Valid InfApiAccessLogExportReqVO exportReqVO,
                                        HttpServletResponse response) throws IOException {
        List<InfApiAccessLog> list = apiAccessLogService.getApiAccessLogList(exportReqVO);
        // 导出 Excel
        List<InfApiAccessLogExcelVO> datas = InfApiAccessLogConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "API 访问日志.xls", "数据", InfApiAccessLogExcelVO.class, datas);
    }

}
