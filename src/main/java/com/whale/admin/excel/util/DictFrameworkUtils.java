package com.whale.admin.excel.util;

import com.whale.admin.excel.dto.DictDataRespDTO;
import com.whale.admin.excel.service.DictDataFrameworkService;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典工具类
 */
@Slf4j
public class DictFrameworkUtils {

    private static DictDataFrameworkService service;

    public static void init(DictDataFrameworkService service) {
        DictFrameworkUtils.service = service;
        log.info("[init][初始化 DictFrameworkUtils 成功]");
    }

    public static DictDataRespDTO getDictDataFromCache(String type, String value) {
        return service.getDictDataFromCache(type, value);
    }

    public static DictDataRespDTO parseDictDataFromCache(String type, String label) {
        return service.parseDictDataFromCache(type, label);
    }

}
