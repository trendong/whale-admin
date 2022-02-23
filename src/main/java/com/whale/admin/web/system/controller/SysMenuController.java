package com.whale.admin.web.system.controller;

import com.whale.admin.web.system.convert.SysMenuConvert;
import com.whale.admin.web.system.service.ISysMenuService;
import com.whale.admin.web.system.vo.menu.*;
import com.whale.framework.common.enums.CommonStatusEnum;
import com.whale.framework.common.pojo.CommonResult;
import com.whale.framework.repository.vo.system.menu.SysMenuListReqVO;
import com.whale.framework.repository.model.krplus.SysMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static com.whale.framework.common.pojo.CommonResult.success;

@Api(tags = "菜单")
@RestController
@RequestMapping("/system/menu")
@Validated
public class SysMenuController {

    @Resource
    private ISysMenuService iSysMenuService;

    @PostMapping("/create")
    @ApiOperation("创建菜单")
//    @PreAuthorize("@ss.hasPermission('system:menu:create')")
    public CommonResult<Long> createMenu(@Valid @RequestBody SysMenuCreateReqVO reqVO) {
        Long menuId = iSysMenuService.createMenu(reqVO);
        return success(menuId);
    }

    @PutMapping("/update")
    @ApiOperation("修改菜单")
//    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody SysMenuUpdateReqVO reqVO) {
        iSysMenuService.updateMenu(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除菜单")
    @ApiImplicitParam(name = "id", value = "角色编号", required= true, example = "1024", dataTypeClass = Long.class)
//    @PreAuthorize("@ss.hasPermission('system:menu:delete')")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        iSysMenuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/list")
    @ApiOperation("获取菜单列表")
//    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<List<SysMenuRespVO>> getMenus(SysMenuListReqVO reqVO) {
        List<SysMenu> list = iSysMenuService.getMenus(reqVO);
        list.sort(Comparator.comparing(SysMenu::getSort));
        return success(SysMenuConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-simple")
    @ApiOperation(value = "获取菜单精简信息列表", notes = "只包含被开启的菜单，主要用于前端的下拉选项")
    public CommonResult<List<SysMenuSimpleRespVO>> getSimpleMenus() {
        // 获得菜单列表，只要开启状态的
        SysMenuListReqVO reqVO = new SysMenuListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<SysMenu> list = iSysMenuService.getMenus(reqVO);
        // 排序
        list.sort(Comparator.comparing(SysMenu::getSort));
        return success(SysMenuConvert.INSTANCE.convertList01(list));
    }

    @GetMapping("/get")
    @ApiOperation("获取菜单信息")
//    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<SysMenuRespVO> getMenu(Long id) {
        SysMenu menu = iSysMenuService.getMenu(id);
        return success(SysMenuConvert.INSTANCE.convert(menu));
    }

}
