package com.whale.admin.web.system.vo.dict.type;

import com.alibaba.excel.annotation.ExcelProperty;
import com.whale.framework.excel.core.annotations.DictFormat;
import com.whale.framework.excel.core.convert.DictConvert;
import com.whale.admin.web.system.enums.SysDictTypeConstants;
import lombok.Data;

/**
 * 字典类型 Excel 导出响应 VO
 */
@Data
public class SysDictTypeExcelVO {

    @ExcelProperty("字典主键")
    private Long id;

    @ExcelProperty("字典名称")
    private String name;

    @ExcelProperty("字典类型")
    private String type;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(SysDictTypeConstants.COMMON_STATUS)
    private Integer status;

}
