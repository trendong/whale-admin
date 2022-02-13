package com.whale.admin.web.system.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.whale.admin.common.util.ExcelUtils;
import com.whale.admin.web.system.convert.SysRoleConvert;
import com.whale.admin.web.system.vo.*;
import com.whale.admin.web.system.service.ISysRoleService;
import com.whale.framework.common.enums.CommonStatusEnum;
import com.whale.framework.common.util.json.JsonUtils;
import com.whale.framework.mybatis.core.type.JsonLongSetTypeHandler;
import com.whale.framework.repository.common.vo.CommonResult;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.system.SysRoleExportReqVO;
import com.whale.framework.repository.common.vo.system.SysRolePageReqVO;
import com.whale.framework.repository.model.krplus.SysRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.whale.framework.repository.common.vo.CommonResult.success;

@Api(tags = "角色")
@RestController
@RequestMapping("/system/role")
@Validated
public class SysRoleController {

    private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Resource
    private ISysRoleService iSysRoleService;

    @PostMapping("/create")
    @ApiOperation("创建角色")
    public CommonResult<Long> createRole(@Valid @RequestBody SysRoleCreateReqVO reqVO) {
        return success(iSysRoleService.createRole(reqVO));
    }

    @PutMapping("/update")
    @ApiOperation("修改角色")
    public CommonResult<Boolean> updateRole(@Valid @RequestBody SysRoleUpdateReqVO reqVO) {
        iSysRoleService.updateRole(reqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改角色状态")
    public CommonResult<Boolean> updateRoleStatus(@Valid @RequestBody SysRoleUpdateStatusReqVO reqVO) {
        iSysRoleService.updateRoleStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "id", value = "角色编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteRole(@RequestParam("id") Long id) {
        iSysRoleService.deleteRole(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得角色信息")
    public CommonResult<SysRoleRespVO> getRole(@RequestParam("id") Long id) {
        SysRole role = iSysRoleService.getRole(id);
        return success(SysRoleConvert.INSTANCE.convert(role));
    }

    @GetMapping("/page")
    @ApiOperation("获得角色分页")
    public CommonResult<PageResult<SysRole>> getRolePage(SysRolePageReqVO reqVO) {
        TypeReference<Set<Long>> typeReference = new TypeReference<Set<Long>>() {};
        String test = "[102, 108, 109]";
        Set<Long> longs = JsonUtils.parseObject(test, typeReference);
        System.out.println(longs);
        logger.info("获得角色分页: {}", JsonUtils.toJsonString(reqVO));
        return success(iSysRoleService.getRolePage(reqVO));
    }


    @GetMapping("/list-simple")
    @ApiOperation(value = "获取角色精简信息列表", notes = "只包含被开启的角色，主要用于前端的下拉选项")
    public CommonResult<List<SysRoleSimpleRespVO>> getSimpleRoles() {
        // 获得角色(状态开启)列表
        List<SysRole> list = iSysRoleService.getRoles(Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序
        list.sort(Comparator.comparing(SysRole::getSort));
        return success(SysRoleConvert.INSTANCE.convertList01(list));
    }

    @GetMapping("/export")
//    @OperateLog(type = EXPORT)
//    @PreAuthorize("@ss.hasPermission('system:role:export')")
    public void export(HttpServletResponse response, @Validated SysRoleExportReqVO reqVO) throws IOException {
        List<SysRole> list = iSysRoleService.getRoles(reqVO);
        List<SysRoleExcelVO> data = SysRoleConvert.INSTANCE.convertList02(list);
        // 输出
        ExcelUtils.write(response, "角色数据.xls", "角色列表", SysRoleExcelVO.class, data);
    }
}
