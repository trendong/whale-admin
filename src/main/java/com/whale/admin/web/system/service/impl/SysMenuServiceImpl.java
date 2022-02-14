package com.whale.admin.web.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.common.annotations.VisibleForTesting;
import com.whale.admin.common.util.CollectionUtils;
import com.whale.admin.web.system.convert.SysMenuConvert;
import com.whale.admin.web.system.enums.system.MenuIdEnum;
import com.whale.admin.web.system.enums.system.MenuTypeEnum;
import com.whale.admin.web.system.service.ISysMenuService;
import com.whale.admin.web.system.vo.menu.SysMenuCreateReqVO;
import com.whale.admin.web.system.vo.menu.SysMenuUpdateReqVO;
import com.whale.framework.repository.common.exception.util.ServiceExceptionUtil;
import com.whale.framework.repository.common.vo.system.menu.SysMenuListReqVO;
import com.whale.framework.repository.mapper.krplus.SysMenuMapper;
import com.whale.framework.repository.model.krplus.SysMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.whale.admin.web.system.enums.system.SysErrorCodeConstants.*;

/**
 * 菜单 Service 实现
 *
 * @author trendong
 */
@Service
@DS("slave_2")
public class SysMenuServiceImpl implements ISysMenuService {

    @Resource
    private SysMenuMapper menuMapper;
//    @Resource
//    private SysPermissionService permissionService;

//    @Resource
//    private SysMenuProducer menuProducer;


    @Override
    public Long createMenu(SysMenuCreateReqVO reqVO) {
        // 校验父菜单存在
        checkParentResource(reqVO.getParentId(), null);
        // 校验菜单（自己）
        checkResource(reqVO.getParentId(), reqVO.getName(), null);
        // 插入数据库
        SysMenu menu = SysMenuConvert.INSTANCE.convert(reqVO);
        initMenuProperty(menu);
        menuMapper.insert(menu);
        return menu.getId();
    }

    @Override
    public void updateMenu(SysMenuUpdateReqVO reqVO) {
        // 校验更新的菜单是否存在
        if (menuMapper.selectById(reqVO.getId()) == null) {
            throw ServiceExceptionUtil.exception(MENU_NOT_EXISTS);
        }
        // 校验父菜单存在
        checkParentResource(reqVO.getParentId(), reqVO.getId());
        // 校验菜单（自己）
        checkResource(reqVO.getParentId(), reqVO.getName(), reqVO.getId());
        // 更新到数据库
        SysMenu updateObject = SysMenuConvert.INSTANCE.convert(reqVO);
        initMenuProperty(updateObject);
        menuMapper.updateById(updateObject);
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        // 校验是否还有子菜单
        if (menuMapper.selectCountByParentId(menuId) > 0) {
            throw ServiceExceptionUtil.exception(MENU_EXISTS_CHILDREN);
        }
        // 校验删除的菜单是否存在
        if (menuMapper.selectById(menuId) == null) {
            throw ServiceExceptionUtil.exception(MENU_NOT_EXISTS);
        }
        // 标记删除
        menuMapper.deleteById(menuId);
        // 删除授予给角色的权限
//        permissionService.processMenuDeleted(menuId);
        // 发送刷新消息. 注意，需要事务提交后，在进行发送刷新消息。不然 db 还未提交，结果缓存先刷新了
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//
//            @Override
//            public void afterCommit() {
//                menuProducer.sendMenuRefreshMessage();
//            }
//
//        });
    }

    @Override
    public List<SysMenu> getMenus() {
        return menuMapper.selectList();
    }

    @Override
    public List<SysMenu> getMenus(SysMenuListReqVO reqVO) {
        return menuMapper.selectList(reqVO);
    }

    @Override
    public List<SysMenu> listMenusFromCache(Collection<Integer> menuTypes, Collection<Integer> menusStatuses) {
        // 任一一个参数为空，则返回空
        if (CollectionUtils.isAnyEmpty(menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }
        // 创建新数组，避免缓存被修改
        return null;
        /*return menuCache.values().stream().filter(menu -> menuTypes.contains(menu.getType())
                && menusStatuses.contains(menu.getStatus()))
                .collect(Collectors.toList());*/
    }

    @Override
    public List<SysMenu> listMenusFromCache(Collection<Long> menuIds, Collection<Integer> menuTypes,
                                              Collection<Integer> menusStatuses) {
        // 任一一个参数为空，则返回空
        if (CollectionUtils.isAnyEmpty(menuIds, menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
        /*return menuCache.values().stream().filter(menu -> menuIds.contains(menu.getId())
                && menuTypes.contains(menu.getType())
                && menusStatuses.contains(menu.getStatus()))
                .collect(Collectors.toList());*/
    }

    @Override
    public List<SysMenu> getMenuListByPermissionFromCache(String permission) {
        return new ArrayList<>();
//        return new ArrayList<>(permissionMenuCache.get(permission));
    }

    @Override
    public SysMenu getMenu(Long id) {
        return menuMapper.selectById(id);
    }

    /**
     * 校验父菜单是否合法
     *
     * 1. 不能设置自己为父菜单
     * 2. 父菜单不存在
     * 3. 父菜单必须是 {@link MenuTypeEnum#MENU} 菜单类型
     *
     * @param parentId 父菜单编号
     * @param childId 当前菜单编号
     */
    @VisibleForTesting
    public void checkParentResource(Long parentId, Long childId) {
        if (parentId == null || MenuIdEnum.ROOT.getId().equals(parentId)) {
            return;
        }
        // 不能设置自己为父菜单
        if (parentId.equals(childId)) {
            throw ServiceExceptionUtil.exception(MENU_PARENT_ERROR);
        }
        SysMenu menu = menuMapper.selectById(parentId);
        // 父菜单不存在
        if (menu == null) {
            throw ServiceExceptionUtil.exception(MENU_PARENT_NOT_EXISTS);
        }
        // 父菜单必须是目录或者菜单类型
        if (!MenuTypeEnum.DIR.getType().equals(menu.getMenuType())
            && !MenuTypeEnum.MENU.getType().equals(menu.getMenuType())) {
            throw ServiceExceptionUtil.exception(MENU_PARENT_NOT_DIR_OR_MENU);
        }
    }

    /**
     * 校验菜单是否合法
     *
     * 1. 校验相同父菜单编号下，是否存在相同的菜单名
     *
     * @param name 菜单名字
     * @param parentId 父菜单编号
     * @param id 菜单编号
     */
    @VisibleForTesting
    public void checkResource(Long parentId, String name, Long id) {
        SysMenu menu = menuMapper.selectByParentIdAndName(parentId, name);
        if (menu == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的菜单
        if (id == null) {
            throw ServiceExceptionUtil.exception(MENU_NAME_DUPLICATE);
        }
        if (!menu.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(MENU_NAME_DUPLICATE);
        }
    }

    /**
     * 初始化菜单的通用属性。
     *
     * 例如说，只有目录或者菜单类型的菜单，才设置 icon
     *
     * @param menu 菜单
     */
    private void initMenuProperty(SysMenu menu) {
        // 菜单为按钮类型时，无需 component、icon、path 属性，进行置空
        if (MenuTypeEnum.BUTTON.getType().equals(menu.getMenuType())) {
            menu.setComponent("");
            menu.setIcon("");
            menu.setPath("");
        }
    }

}
