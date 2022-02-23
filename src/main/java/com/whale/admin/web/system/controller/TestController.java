package com.whale.admin.web.system.controller;

import com.whale.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import static com.whale.framework.common.pojo.CommonResult.success;

@Api(tags = "字典数据")
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/dict")
    public CommonResult<String> selectList() {
        return success("");
    }

}
