package com.whale.admin.web.system.vo.role;

import com.alibaba.excel.annotation.ExcelProperty;
import com.whale.framework.excel.core.annotations.DictFormat;
import com.whale.framework.excel.core.convert.DictConvert;
import com.whale.admin.web.system.enums.SysDictTypeConstants;
import lombok.Data;

/**
 * 角色 Excel 导出响应 VO
 */
@Data
public class SysRoleExcelVO {

    @ExcelProperty("角色序号")
    private Long id;

    @ExcelProperty("角色名称")
    private String name;

    @ExcelProperty("角色标志")
    private String code;

    @ExcelProperty("角色排序")
    private Integer sort;

    @ExcelProperty("数据范围")
    private Integer dataScope;

    @ExcelProperty(value = "角色状态", converter = DictConvert.class)
    @DictFormat(SysDictTypeConstants.COMMON_STATUS)
    private String status;

}
