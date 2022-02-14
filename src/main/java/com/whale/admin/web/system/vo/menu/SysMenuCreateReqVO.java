package com.whale.admin.web.system.vo.menu;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("菜单创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuCreateReqVO extends SysMenuBaseVO {
}
