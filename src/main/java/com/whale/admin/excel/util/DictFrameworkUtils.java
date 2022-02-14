package com.whale.admin.excel.util;

import com.whale.admin.excel.dto.DictDataRespDTO;
import com.whale.admin.excel.service.DictDataFrameworkService;
import com.whale.admin.web.system.controller.SysRoleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字典工具类
 */
public class DictFrameworkUtils {

    private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    private static DictDataFrameworkService service;

    public static void init(DictDataFrameworkService service) {
        DictFrameworkUtils.service = service;
        logger.info("[init][初始化 DictFrameworkUtils 成功]");
    }

    public static DictDataRespDTO getDictDataFromCache(String type, String value) {
        return service.getDictDataFromCache(type, value);
    }

    public static DictDataRespDTO parseDictDataFromCache(String type, String label) {
        return service.parseDictDataFromCache(type, label);
    }

}
