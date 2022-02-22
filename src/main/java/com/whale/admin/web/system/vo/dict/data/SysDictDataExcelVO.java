package com.whale.admin.web.system.vo.dict.data;

import com.alibaba.excel.annotation.ExcelProperty;
import com.whale.admin.excel.annotations.DictFormat;
import com.whale.admin.excel.core.DictConvert;
import com.whale.admin.web.system.enums.SysDictTypeConstants;
import lombok.Data;

/**
 * 字典数据 Excel 导出响应 VO
 */
@Data
public class SysDictDataExcelVO {

    @ExcelProperty("字典编码")
    private Long id;

    @ExcelProperty("字典排序")
    private Integer sort;

    @ExcelProperty("字典标签")
    private String label;

    @ExcelProperty("字典键值")
    private String value;

    @ExcelProperty("字典类型")
    private String dictType;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(SysDictTypeConstants.COMMON_STATUS)
    private Integer status;

}
