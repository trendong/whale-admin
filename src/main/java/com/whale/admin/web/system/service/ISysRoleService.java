package com.whale.admin.web.system.service;

import com.whale.admin.web.system.vo.SysRoleCreateReqVO;
import com.whale.framework.repository.common.vo.system.SysRoleExportReqVO;
import com.whale.admin.web.system.vo.SysRoleUpdateReqVO;
import com.whale.framework.repository.common.vo.PageResult;
import com.whale.framework.repository.common.vo.system.SysRolePageReqVO;
import com.whale.framework.repository.model.krplus.SysRole;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * 角色 Service 接口
 *
 * @author trendong
 */
public interface ISysRoleService {

    /**
     * 创建角色
     *
     * @param reqVO 创建角色信息
     * @return 角色编号
     */
    Long createRole(SysRoleCreateReqVO reqVO);

    /**
     * 更新角色
     *
     * @param reqVO 更新角色信息
     */
    void updateRole(SysRoleUpdateReqVO reqVO);

    /**
     * 更新角色状态
     *
     * @param id 角色编号
     * @param status 状态
     */
    void updateRoleStatus(Long id, Integer status);

    /**
     * 删除角色
     *
     * @param id 角色编号
     */
    void deleteRole(Long id);

    /**
     * 获得角色
     *
     * @param id 角色编号
     * @return 角色
     */
    SysRole getRole(Long id);

    /**
     * 获得角色分页
     *
     * @param reqVO 角色分页查询
     * @return 角色分页结果
     */
    PageResult<SysRole> getRolePage(SysRolePageReqVO reqVO);

    /**
     * 获得角色列表
     *
     * @param statuses 筛选的状态。允许空，空时不筛选
     * @return 角色列表
     */
    List<SysRole> getRoles(@Nullable Collection<Integer> statuses);

    /**
     * 获得角色列表
     *
     * @param reqVO 列表查询
     * @return 角色列表
     */
    List<SysRole> getRoles(SysRoleExportReqVO reqVO);

}
