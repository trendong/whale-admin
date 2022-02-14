package com.whale.admin.web.system.controller;

import com.whale.admin.web.system.service.ISysPermissionService;
import com.whale.admin.web.system.vo.permission.SysPermissionAssignRoleDataScopeReqVO;
import com.whale.admin.web.system.vo.permission.SysPermissionAssignRoleMenuReqVO;
import com.whale.admin.web.system.vo.permission.SysPermissionAssignUserRoleReqVO;
import com.whale.framework.repository.common.vo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

import static com.whale.framework.repository.common.vo.CommonResult.success;

/**
 * 权限 Controller，提供赋予用户、角色的权限的 API 接口
 *
 * @author trendong
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/system/permission")
public class SysPermissionController {

    @Resource
    private ISysPermissionService iSysPermissionService;

    @ApiOperation("获得角色拥有的菜单编号")
    @ApiImplicitParam(name = "roleId", value = "角色编号", required = true, dataTypeClass = Long.class)
    @GetMapping("/list-role-resources")
//    @RequiresPermissions("system:permission:assign-role-menu")
    public CommonResult<Set<Long>> listRoleMenus(Long roleId) {
        return success(iSysPermissionService.listRoleMenuIds(roleId));
    }

    @PostMapping("/assign-role-menu")
    @ApiOperation("赋予角色菜单")
//    @RequiresPermissions("system:permission:assign-role-resource")
    public CommonResult<Boolean> assignRoleMenu(@Validated @RequestBody SysPermissionAssignRoleMenuReqVO reqVO) {
        iSysPermissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return success(true);
    }

    @PostMapping("/assign-role-data-scope")
    @ApiOperation("赋予角色数据权限")
//    @RequiresPermissions("system:permission:assign-role-data-scope")
    public CommonResult<Boolean> assignRoleDataScope(
            @Validated @RequestBody SysPermissionAssignRoleDataScopeReqVO reqVO) {
        iSysPermissionService.assignRoleDataScope(reqVO.getRoleId(), reqVO.getDataScope(), reqVO.getDataScopeDeptIds());
        return success(true);
    }

    @ApiOperation("获得管理员拥有的角色编号列表")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataTypeClass = Long.class)
    @GetMapping("/list-user-roles")
//    @RequiresPermissions("system:permission:assign-user-role")
    public CommonResult<Set<Long>> listAdminRoles(@RequestParam("userId") Long userId) {
        return success(iSysPermissionService.listUserRoleIs(userId));
    }

    @ApiOperation("赋予用户角色")
    @PostMapping("/assign-user-role")
//    @RequiresPermissions("system:permission:assign-user-role")
    public CommonResult<Boolean> assignUserRole(@Validated @RequestBody SysPermissionAssignUserRoleReqVO reqVO) {
        iSysPermissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return success(true);
    }

}
