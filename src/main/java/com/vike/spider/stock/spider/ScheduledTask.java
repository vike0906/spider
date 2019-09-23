package com.vike.spider.stock.spider;

import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.repository.BaseStockInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lsl
 * @createDate: 2019/9/24
 */
@Slf4j
@Component
public class ScheduledTask {

    @Autowired
    BaseStockInfoRepository baseStockInfoRepository;

    /**
     * @description: TODO 更新股票基础数据
     */
    @Scheduled(cron = "0 0 10 ? * MON-FRI")
    public void updateBaseStockInfo(){
        /** 当前时间 */
        long currentTimeMillis = System.currentTimeMillis();

        /** 当前数据库记录的深交所/上交所全部代码 */
        List<BaseStockInfo> sz = baseStockInfoRepository.findAllByExchange("sz");
        List<BaseStockInfo> sh = baseStockInfoRepository.findAllByExchange("sh");

        /** 分别获取深交所/上交所最新代码 */
        List<BaseStockInfo> szNow= StockInfo.szBaseStockInfo().getData().getDiff().stream().map(a->{
            BaseStockInfo bs = new BaseStockInfo();
            bs.setCode(a.getF12()).setName(a.getF14()).setExchange("sz")
                    .setUpdateTime(new Date(currentTimeMillis))
                    .setCreateTime(new Date(currentTimeMillis));
            return bs;
        }).collect(Collectors.toList());

        List<BaseStockInfo> shNow = StockInfo.shBaseStockInfo().getData().getDiff().stream().map(a->{
            BaseStockInfo bs = new BaseStockInfo();
            bs.setCode(a.getF12()).setName(a.getF14()).setExchange("sh")
                    .setUpdateTime(new Date(currentTimeMillis))
                    .setCreateTime(new Date(currentTimeMillis));
            return bs;
        }).collect(Collectors.toList());

        /** 将未记录的股票写入数据库 */
        List<BaseStockInfo> szNew = szNow.stream().filter(a->!sz.contains(a)).collect(Collectors.toList());
        List<BaseStockInfo> shNew = shNow.stream().filter(a->!sh.contains(a)).collect(Collectors.toList());
        szNew.addAll(shNew);
        baseStockInfoRepository.saveAll(szNew);
        log.info("写入未记录的股票写入数据库*****************************************************");

        /**  已写入的刷新更新时间 */
        List<BaseStockInfo> szUpdate = sz.stream().filter(a -> szNow.contains(a)).map(a -> a.setUpdateTime(new Date(currentTimeMillis))).collect(Collectors.toList());
        List<BaseStockInfo> shUpdate = sz.stream().filter(a -> shNow.contains(a)).map(a -> a.setUpdateTime(new Date(currentTimeMillis))).collect(Collectors.toList());
        szUpdate.addAll(shUpdate);
        baseStockInfoRepository.saveAll(szUpdate);
        log.info("已写入的刷新更新时间***************************************************");

        /**  删除退市的股票信息 */
        List<BaseStockInfo> szDelete = sz.stream().filter(a -> !szNow.contains(a)).collect(Collectors.toList());
        List<BaseStockInfo> shDelete = sz.stream().filter(a -> !shNow.contains(a)).collect(Collectors.toList());
        szDelete.addAll(shDelete);
        baseStockInfoRepository.deleteAll(szDelete);
        log.info("删除退市的股票信息********************************************************");


    }
}
