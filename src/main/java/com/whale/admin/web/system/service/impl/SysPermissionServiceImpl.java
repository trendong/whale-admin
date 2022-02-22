package com.whale.admin.web.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.whale.admin.common.util.CollectionUtils;
import com.whale.admin.web.system.service.ISysMenuService;
import com.whale.admin.web.system.service.ISysPermissionService;
import com.whale.admin.web.system.service.ISysRoleService;
import com.whale.framework.repository.mapper.krplus.SysRoleMenuMapper;
import com.whale.framework.repository.mapper.krplus.SysUserRoleMapper;
import com.whale.framework.repository.model.krplus.SysMenu;
import com.whale.framework.repository.model.krplus.SysRole;
import com.whale.framework.repository.model.krplus.SysRoleMenu;
import com.whale.framework.repository.model.krplus.SysUserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Resource
    private SysRoleMenuMapper roleMenuMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private ISysRoleService iSysRoleService;
    @Resource
    private ISysMenuService iSysMenuService;

    /**
     * 初始化 {@link #roleMenuCache} 和 {@link #menuRoleCache} 缓存
     */
    @Override
    @PostConstruct
    public void initLocalCache() {

    }

    /**
     * 如果角色与菜单的关联发生变化，从数据库中获取最新的全量角色与菜单的关联。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前角色与菜单的关联的最大更新时间
     * @return 角色与菜单的关联列表
     */
    private List<SysRoleMenu> loadRoleMenuIfUpdate(Date maxUpdateTime) {
        return Collections.emptyList();
    }

    @Override
    public List<SysMenu> getRoleMenusFromCache(Collection<Long> roleIds, Collection<Integer> menuTypes,
                                               Collection<Integer> menusStatuses) {
        return Collections.emptyList();
    }

    @Override
    public Set<Long> getUserRoleIds(Long userId, Collection<Integer> roleStatuses) {
        List<SysUserRole> userRoleList = userRoleMapper.selectListByUserId(userId);
        // 过滤角色状态
        if (CollectionUtil.isNotEmpty(roleStatuses)) {
            userRoleList.removeIf(userRoleDO -> {
                SysRole role = iSysRoleService.getRoleFromCache(userRoleDO.getRoleId());
                return role == null || !roleStatuses.contains(role.getStatus());
            });
        }
        return CollectionUtils.convertSet(userRoleList, SysUserRole::getRoleId);
    }

    @Override
    public Set<Long> listRoleMenuIds(Long roleId) {
        // 如果是管理员的情况下，获取全部菜单编号
        SysRole role = iSysRoleService.getRole(roleId);
        if (iSysRoleService.hasAnyAdmin(Collections.singletonList(role))) {
            return CollectionUtils.convertSet(iSysMenuService.getMenus(), SysMenu::getId);
        }
        // 如果是非管理员的情况下，获得拥有的菜单编号
        return CollectionUtils.convertSet(roleMenuMapper.selectListByRoleId(roleId),
                SysRoleMenu::getMenuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoleMenu(Long roleId, Set<Long> menuIds) {
        // 获得角色拥有菜单编号
        Set<Long> dbMenuIds = CollectionUtils.convertSet(roleMenuMapper.selectListByRoleId(roleId),
                SysRoleMenu::getMenuId);
        // 计算新增和删除的菜单编号
        Collection<Long> createMenuIds = CollUtil.subtract(menuIds, dbMenuIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createMenuIds)) {
            roleMenuMapper.insertList(roleId, createMenuIds);
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            roleMenuMapper.deleteListByRoleIdAndMenuIds(roleId, deleteMenuIds);
        }
        // 发送刷新消息. 注意，需要事务提交后，在进行发送刷新消息。不然 db 还未提交，结果缓存先刷新了
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//
//            @Override
//            public void afterCommit() {
//                permissionProducer.sendRoleMenuRefreshMessage();
//            }
//
//        });
    }

    @Override
    public Set<Long> listUserRoleIs(Long userId) {
        return CollectionUtils.convertSet(userRoleMapper.selectListByUserId(userId),
                SysUserRole::getRoleId);
    }

    @Override
    public void assignUserRole(Long userId, Set<Long> roleIds) {
        // 获得角色拥有角色编号
        Set<Long> dbRoleIds = CollectionUtils.convertSet(userRoleMapper.selectListByUserId(userId),
                SysUserRole::getRoleId);
        // 计算新增和删除的角色编号
        Collection<Long> createRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbRoleIds, roleIds);
        // 执行新增和删除。对于已经授权的角色，不用做任何处理
        if (!CollectionUtil.isEmpty(createRoleIds)) {
            userRoleMapper.insertList(userId, createRoleIds);
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            userRoleMapper.deleteListByUserIdAndRoleIdIds(userId, deleteMenuIds);
        }
    }

    @Override
    public void assignRoleDataScope(Long roleId, Integer dataScope, Set<Long> dataScopeDeptIds) {
        iSysRoleService.updateRoleDataScope(roleId, dataScope, dataScopeDeptIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processRoleDeleted(Long roleId) {
        // 标记删除 UserRole
        userRoleMapper.deleteListByRoleId(roleId);
        // 标记删除 RoleMenu
        roleMenuMapper.deleteListByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processMenuDeleted(Long menuId) {
        roleMenuMapper.deleteListByMenuId(menuId);
    }

    @Override
    public void processUserDeleted(Long userId) {
        userRoleMapper.deleteListByUserId(userId);
    }

    /*@Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(permissions)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<Long> roleIds = new HashSet<>();
//        Set<Long> roleIds = SecurityFrameworkUtils.getLoginUserRoleIds();
//        if (CollUtil.isEmpty(roleIds)) {
//            return false;
//        }
        // 判断是否是超管。如果是，当然符合条件
        if (iSysRoleService.hasAnyAdmin(roleIds)) {
            return true;
        }

        // 遍历权限，判断是否有一个满足
        return Arrays.stream(permissions).anyMatch(permission -> {
            List<SysMenu> menuList = iSysMenuService.getMenuListByPermissionFromCache(permission);
            // 采用严格模式，如果权限找不到对应的 Menu 的话，认为
            if (CollUtil.isEmpty(menuList)) {
                return false;
            }
            // 获得是否拥有该权限，任一一个
//            return menuList.stream().anyMatch(menu -> CollUtil.containsAny(roleIds,
//                    menuRoleCache.get(menu.getId())));
        });
    }*/

    /*@Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    public boolean hasAnyRoles(String... roles) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(roles)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<Long> roleIds = new HashSet<>();
//        Set<Long> roleIds = SecurityFrameworkUtils.getLoginUserRoleIds();
//        if (CollUtil.isEmpty(roleIds)) {
//            return false;
//        }
        // 判断是否是超管。如果是，当然符合条件
        if (iSysRoleService.hasAnyAdmin(roleIds)) {
            return true;
        }
        Set<String> userRoles = CollectionUtils.convertSet(iSysRoleService.getRolesFromCache(roleIds),
                SysRole::getCode);
        return CollUtil.containsAny(userRoles, Sets.newHashSet(roles));
    }*/

}
