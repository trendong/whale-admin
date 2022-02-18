package com.whale.admin.config.operatelog.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.whale.admin.WhaleAdminApplication;
import com.whale.admin.config.operatelog.convert.SysOperateLogCoreConvert;
import com.whale.admin.config.operatelog.service.ISysOperateLogCoreService;
import com.whale.framework.common.util.string.StrUtils;
import com.whale.framework.operatelog.core.dto.OperateLogCreateReqDTO;
import com.whale.framework.repository.mapper.krplus.SysOperateLogMapper;
import com.whale.framework.repository.model.krplus.SysOperateLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Future;

import static com.whale.framework.common.constant.WhaleConstants.JAVA_METHOD_ARGS_MAX_LENGTH;
import static com.whale.framework.common.constant.WhaleConstants.RESULT_MAX_LENGTH;

@Service
@DS("slave_2")
public class SysOperateLogCoreServiceImpl implements ISysOperateLogCoreService {

    private static Logger logger = LoggerFactory.getLogger(WhaleAdminApplication.class);

    @Resource
    private SysOperateLogMapper operateLogMapper;

    @Override
    @Async
    public Future<Boolean> createOperateLogAsync(OperateLogCreateReqDTO reqVO) {
        boolean success = false;
        try {
            SysOperateLog sysOperateLog = SysOperateLogCoreConvert.INSTANCE.convert(reqVO);
            sysOperateLog.setJavaMethodArgs(StrUtils.maxLength(sysOperateLog.getJavaMethodArgs(), JAVA_METHOD_ARGS_MAX_LENGTH));
            sysOperateLog.setResultData(StrUtils.maxLength(sysOperateLog.getResultData(), RESULT_MAX_LENGTH));
            success = operateLogMapper.insert(sysOperateLog) == 1;
        } catch (Throwable throwable) {
            // 仅仅打印日志，不对外抛出。原因是，还是要保留现场数据。
            logger.error("[createOperateLogAsync][记录操作日志异常，日志为 ({})]", reqVO, throwable);
        }
        return new AsyncResult<>(success);
    }
}
