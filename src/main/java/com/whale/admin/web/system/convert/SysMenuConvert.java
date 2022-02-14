package com.whale.admin.web.system.convert;

import com.whale.admin.web.system.vo.menu.SysMenuCreateReqVO;
import com.whale.admin.web.system.vo.menu.SysMenuRespVO;
import com.whale.admin.web.system.vo.menu.SysMenuSimpleRespVO;
import com.whale.admin.web.system.vo.menu.SysMenuUpdateReqVO;
import com.whale.framework.repository.model.krplus.SysMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysMenuConvert {

    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    List<SysMenuRespVO> convertList(List<SysMenu> list);

    SysMenu convert(SysMenuCreateReqVO bean);

    SysMenu convert(SysMenuUpdateReqVO bean);

    SysMenuRespVO convert(SysMenu bean);

    List<SysMenuSimpleRespVO> convertList01(List<SysMenu> list);

}
