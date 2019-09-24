package com.vike.spider.stock.spider;

import com.vike.spider.stock.CommonStatus;
import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.repository.BaseStockInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lsl
 * @createDate: 2019/9/24
 */
@Slf4j
@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    BaseStockInfoRepository baseStockInfoRepository;

    /**
     * @description: TODO 更新股票基础数据
     */
    @Scheduled(cron = "0 0 18 ? * 2-6")
    public void updateBaseStockInfo(){
        /** 当前时间 */
        long currentTimeMillis = System.currentTimeMillis();

        /** 当前数据库记录的深交所/上交所全部代码 */
        List<BaseStockInfo> szDataBase = baseStockInfoRepository.findAllByExchange("sz");
        List<BaseStockInfo> shDataBase = baseStockInfoRepository.findAllByExchange("sh");

        /** 分别获取深交所/上交所最新代码 */
        List<BaseStockInfo> szNow= StockInfo.szBaseStockInfo().getData().getDiff().stream().map(a->{
            BaseStockInfo bs = new BaseStockInfo();
            bs.setCode(a.getF12()).setName(a.getF14()).setExchange("sz").setIsExist(CommonStatus.NORMAL.getCode())
                    .setUpdateTime(new Date(currentTimeMillis))
                    .setCreateTime(new Date(currentTimeMillis));
            return bs;
        }).collect(Collectors.toList());

        List<BaseStockInfo> shNow = StockInfo.shBaseStockInfo().getData().getDiff().stream().map(a->{
            BaseStockInfo bs = new BaseStockInfo();
            bs.setCode(a.getF12()).setName(a.getF14()).setExchange("sh").setIsExist(CommonStatus.NORMAL.getCode())
                    .setUpdateTime(new Date(currentTimeMillis))
                    .setCreateTime(new Date(currentTimeMillis));
            return bs;
        }).collect(Collectors.toList());

        Assert.notEmpty(szNow,"深交所股票信息获取失败");
        Assert.notEmpty(shNow,"上交所股票信息获取失败");

        /** 将未记录的股票写入数据库 */
        List<BaseStockInfo> szNew = szNow.stream().filter(a->!szDataBase.contains(a)).collect(Collectors.toList());
        List<BaseStockInfo> shNew = shNow.stream().filter(a->!shDataBase.contains(a)).collect(Collectors.toList());
        szNew.addAll(shNew);
        List<BaseStockInfo> baseStockInfos2 = baseStockInfoRepository.saveAll(szNew);
        log.info("******写入未记录的股票写入数据库--{}条记录写入************",baseStockInfos2.size());

        /**  已写入的刷新更新时间 */
        List<BaseStockInfo> szUpdate = szDataBase.stream().filter(a -> szNow.contains(a)).map(a -> a.setUpdateTime(new Date(currentTimeMillis))).collect(Collectors.toList());
        List<BaseStockInfo> shUpdate = shDataBase.stream().filter(a -> shNow.contains(a)).map(a -> a.setUpdateTime(new Date(currentTimeMillis))).collect(Collectors.toList());
        szUpdate.addAll(shUpdate);
        List<BaseStockInfo> baseStockInfos = baseStockInfoRepository.saveAll(szUpdate);
        log.info("******刷新已记录股票更新时间--{}条记录更新******", baseStockInfos.size());

        /**  删除退市的股票信息 */
        List<BaseStockInfo> szDelete = szDataBase.stream().filter(a -> !szNow.contains(a)).map(a->a.setIsExist(CommonStatus.DELETE.getCode())).collect(Collectors.toList());
        List<BaseStockInfo> shDelete = shDataBase.stream().filter(a -> !shNow.contains(a)).map(a->a.setIsExist(CommonStatus.DELETE.getCode())).collect(Collectors.toList());
        szDelete.addAll(shDelete);
        List<BaseStockInfo> baseStockInfos1 = baseStockInfoRepository.saveAll(szDelete);
        log.info("******删除退市的股票信息--{}条记录删除******",baseStockInfos1.size());

    }
}
