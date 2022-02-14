package com.whale.admin.web.system.controller;

import com.whale.admin.excel.service.ISysDictDataCoreService;
import com.whale.framework.repository.common.vo.CommonResult;
import com.whale.framework.repository.mapper.krplus.custom.SysDictDataCoreMapper;
import com.whale.framework.repository.model.krplus.SysDictData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import static com.whale.framework.repository.common.vo.CommonResult.success;

@Api(tags = "字典数据")
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ISysDictDataCoreService iSysDictDataCoreService;

    @PostMapping("/dict")
    public CommonResult<List<SysDictData>> selectList() {
        List<SysDictData> sysDictData = iSysDictDataCoreService.selectList();
        return success(sysDictData);
    }

}
