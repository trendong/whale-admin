package com.whale.admin.config.dict.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.common.collect.ImmutableTable;
import com.whale.admin.WhaleAdminApplication;
import com.whale.admin.config.dict.convert.SysDictDataCoreConvert;
import com.whale.admin.config.dict.service.ISysDictDataCoreService;
import com.whale.admin.web.system.service.ISysDictDataService;
import com.whale.framework.dict.core.dto.DictDataRespDTO;
import com.whale.framework.repository.model.krplus.SysDictData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 字典数据 Service 实现类
 *
 * @author trendong
 */
@Service
public class SysDictDataCoreServiceImpl implements ISysDictDataCoreService {

    private static Logger logger = LoggerFactory.getLogger(WhaleAdminApplication.class);

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    /**
     * 字典数据缓存，第二个 key 使用 label
     *
     * key1：字典类型 dictType
     * key2：字典标签 label
     */
    private ImmutableTable<String, String, SysDictData> labelDictDataCache;
    /**
     * 字典数据缓存，第二个 key 使用 value
     *
     * key1：字典类型 dictType
     * key2：字典值 value
     */
    private ImmutableTable<String, String, SysDictData> valueDictDataCache;
    /**
     * 缓存字典数据的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    @Resource
    private ISysDictDataService iSysDictDataService;

    @Override
    @PostConstruct
    public synchronized void initLocalCache() {
        // 获取字典数据列表
        List<SysDictData> dataList = this.loadDictDataIfUpdate(maxUpdateTime);
        if (CollUtil.isEmpty(dataList)) {
            return;
        }

        // 构建缓存
        ImmutableTable.Builder<String, String, SysDictData> labelDictDataBuilder = ImmutableTable.builder();
        ImmutableTable.Builder<String, String, SysDictData> valueDictDataBuilder = ImmutableTable.builder();
        dataList.forEach(dictData -> {
            labelDictDataBuilder.put(dictData.getDictType(), dictData.getLabel(), dictData);
            valueDictDataBuilder.put(dictData.getDictType(), dictData.getValue(), dictData);
        });
        labelDictDataCache = labelDictDataBuilder.build();
        valueDictDataCache = valueDictDataBuilder.build();
        assert dataList.size() > 0; // 断言，避免告警
        maxUpdateTime = dataList.stream().max(Comparator.comparing(SysDictData::getUpdateTime)).get().getUpdateTime();
        logger.info("[initLocalCache][缓存字典数据，数量为:{}]", dataList.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        initLocalCache();
    }

    /**
     * 如果字典数据发生变化，从数据库中获取最新的全量字典数据。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前字典数据的最大更新时间
     * @return 字典数据列表
     */
    private List<SysDictData> loadDictDataIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            logger.info("[loadDictDataIfUpdate][首次加载全量字典数据]");
        } else { // 判断数据库中是否有更新的字典数据
            if (!iSysDictDataService.selectExistsByUpdateTimeAfter(maxUpdateTime)) {
                return null;
            }
            logger.info("[loadDictDataIfUpdate][增量加载全量字典数据]");
        }
        // 第二步，如果有更新，则从数据库加载所有字典数据
        return iSysDictDataService.selectList();
    }

    @Override
    public DictDataRespDTO getDictDataFromCache(String type, String value) {
        return SysDictDataCoreConvert.INSTANCE.convert02(valueDictDataCache.get(type, value));
    }

    @Override
    public DictDataRespDTO parseDictDataFromCache(String type, String label) {
        return SysDictDataCoreConvert.INSTANCE.convert02(labelDictDataCache.get(type, label));
    }

    @Override
    public List<DictDataRespDTO> listDictDataFromCache(String type) {
        return SysDictDataCoreConvert.INSTANCE.convertList03(labelDictDataCache.row(type).values());
    }

}
