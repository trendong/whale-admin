package com.whale.admin.web.system.convert;

import com.whale.admin.web.system.vo.*;
import com.whale.framework.repository.model.krplus.SysRole;
import org.mapstruct.Mapper;
import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface SysRoleConvert {

    SysRoleConvert INSTANCE = getMapper(SysRoleConvert.class);

    SysRole convert(SysRoleCreateReqVO bean);
    SysRole convert(SysRoleUpdateReqVO bean);
    SysRoleRespVO convert(SysRole bean);

    List<SysRoleSimpleRespVO> convertList01(List<SysRole> list);
    List<SysRoleExcelVO> convertList02(List<SysRole> list);

}
