package com.whale.admin.web.system.controller;

import com.whale.admin.common.util.ExcelUtils;
import com.whale.admin.web.system.convert.dict.SysDictDataConvert;
import com.whale.admin.web.system.service.ISysDictDataService;
import com.whale.admin.web.system.vo.dict.data.*;
import com.whale.framework.repository.common.vo.CommonResult;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.system.dict.SysDictDataExportReqVO;
import com.whale.framework.repository.common.vo.system.dict.SysDictDataPageReqVO;
import com.whale.framework.repository.model.krplus.SysDictData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.whale.framework.repository.common.vo.CommonResult.success;

@Api(tags = "字典数据")
@RestController
@RequestMapping("/system/dict-data")
@Validated
public class SysDictDataController {

    @Resource
    private ISysDictDataService iSysDictDataService;

    @PostMapping("/create")
    @ApiOperation("新增字典数据")
//    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public CommonResult<Long> createDictData(@Valid @RequestBody SysDictDataCreateReqVO reqVO) {
        Long dictDataId = iSysDictDataService.createDictData(reqVO);
        return success(dictDataId);
    }

    @PutMapping("update")
    @ApiOperation("修改字典数据")
//    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> updateDictData(@Valid @RequestBody SysDictDataUpdateReqVO reqVO) {
        iSysDictDataService.updateDictData(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除字典数据")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
//    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public CommonResult<Boolean> deleteDictData(Long id) {
        iSysDictDataService.deleteDictData(id);
        return success(true);
    }

    @GetMapping("/list-simple")
    @ApiOperation(value = "获得全部字典数据列表", notes = "一般用于管理后台缓存字典数据在本地")
    // 无需添加权限认证，因为前端全局都需要
    public CommonResult<List<SysDictDataSimpleRespVO>> getSimpleDictDatas() {
        List<SysDictData> list = iSysDictDataService.getDictDatas();
        return success(SysDictDataConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("/获得字典类型的分页列表")
//    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<PageResult<SysDictDataRespVO>> getDictTypePage(@Valid SysDictDataPageReqVO reqVO) {
        return success(SysDictDataConvert.INSTANCE.convertPage(iSysDictDataService.getDictDataPage(reqVO)));
    }

    @GetMapping(value = "/get")
    @ApiOperation("/查询字典数据详细")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
//    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<SysDictDataRespVO> getDictData(@RequestParam("id") Long id) {
        return success(SysDictDataConvert.INSTANCE.convert(iSysDictDataService.getDictData(id)));
    }

    @GetMapping("/export")
    @ApiOperation("导出字典数据")
//    @PreAuthorize("@ss.hasPermission('system:dict:export')")
//    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Valid SysDictDataExportReqVO reqVO) throws IOException {
        List<SysDictData> list = iSysDictDataService.getDictDatas(reqVO);
        List<SysDictDataExcelVO> data = SysDictDataConvert.INSTANCE.convertList02(list);
        // 输出
        ExcelUtils.write(response, "字典数据.xls", "数据列表", SysDictDataExcelVO.class, data);
    }

}
