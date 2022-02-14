package com.whale.admin.web.system.controller;

import com.whale.admin.common.util.ExcelUtils;
import com.whale.admin.web.system.convert.dict.SysDictTypeConvert;
import com.whale.admin.web.system.service.ISysDictTypeService;
import com.whale.admin.web.system.vo.dict.type.*;
import com.whale.framework.repository.common.vo.CommonResult;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.system.dict.SysDictTypeExportReqVO;
import com.whale.framework.repository.common.vo.system.dict.SysDictTypePageReqVO;
import com.whale.framework.repository.model.krplus.SysDictType;
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

@Api(tags = "字典类型")
@RestController
@RequestMapping("/system/dict-type")
@Validated
public class SysDictTypeController {

    @Resource
    private ISysDictTypeService iSysDictTypeService;

    @PostMapping("/create")
    @ApiOperation("创建字典类型")
//    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public CommonResult<Long> createDictType(@Valid @RequestBody SysDictTypeCreateReqVO reqVO) {
        Long dictTypeId = iSysDictTypeService.createDictType(reqVO);
        return success(dictTypeId);
    }

    @PutMapping("/update")
    @ApiOperation("修改字典类型")
//    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> updateDictType(@Valid @RequestBody SysDictTypeUpdateReqVO reqVO) {
        iSysDictTypeService.updateDictType(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除字典类型")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
//    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public CommonResult<Boolean> deleteDictType(Long id) {
        iSysDictTypeService.deleteDictType(id);
        return success(true);
    }

    @ApiOperation("/获得字典类型的分页列表")
    @GetMapping("/page")
//    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<PageResult<SysDictTypeRespVO>> pageDictTypes(@Valid SysDictTypePageReqVO reqVO) {
        return success(SysDictTypeConvert.INSTANCE.convertPage(iSysDictTypeService.getDictTypePage(reqVO)));
    }

    @ApiOperation("/查询字典类型详细")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @GetMapping(value = "/get")
//    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<SysDictTypeRespVO> getDictType(@RequestParam("id") Long id) {
        return success(SysDictTypeConvert.INSTANCE.convert(iSysDictTypeService.getDictType(id)));
    }

    @GetMapping("/list-simple")
    @ApiOperation(value = "获得全部字典类型列表", notes = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
    public CommonResult<List<SysDictTypeSimpleRespVO>> listSimpleDictTypes() {
        List<SysDictType> list = iSysDictTypeService.getDictTypeList();
        return success(SysDictTypeConvert.INSTANCE.convertList(list));
    }

    @ApiOperation("导出数据类型")
    @GetMapping("/export")
//    @PreAuthorize("@ss.hasPermission('system:dict:query')")
//    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Valid SysDictTypeExportReqVO reqVO) throws IOException {
        List<SysDictType> list = iSysDictTypeService.getDictTypeList(reqVO);
        List<SysDictTypeExcelVO> data = SysDictTypeConvert.INSTANCE.convertList02(list);
        // 输出
        ExcelUtils.write(response, "字典类型.xls", "类型列表", SysDictTypeExcelVO.class, data);
    }

}
