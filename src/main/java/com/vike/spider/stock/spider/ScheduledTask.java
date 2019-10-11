package com.vike.spider.stock.spider;

import com.vike.spider.common.CommonStatus;
import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.repository.BaseStockInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
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
     * @description: A股所有股票
     */
    @Scheduled(cron = "0 0 15 ? * 2-6")
    public void updateBaseStockInfo(){
        /** 当前时间 */
        long currentTimeMillis = System.currentTimeMillis();

        /** 当前数据库记录的深交所/上交所全部代码 */
        List<BaseStockInfo> szDataBase = baseStockInfoRepository.findAllByExchange("sz");
        List<BaseStockInfo> shDataBase = baseStockInfoRepository.findAllByExchange("sh");

        /** 分别获取深交所/上交所最新代码 */
        List<BaseStockInfo> szNow= StockInfo.szBaseStockInfo().getData().getDiff().stream().map(a->{
            BaseStockInfo bs = new BaseStockInfo();
            bs.setCode(a.getF12()).setName(a.getF14()).setExchange("sz")
                    .setLastPrice(a.getF2()).setChange(a.getF3()).setChangeSixty(a.getF24()).setChangeBeginYear(a.getF25())
                    .setChangeAmount(a.getF4()).setTurnoverRate(a.getF8()).setPe(a.getF9()).setPb(a.getF23())
                    .setCirculationMarketValue(a.getF21()).setTotalValue(a.getF20())
                    .setIsExist(CommonStatus.NORMAL.getCode())
                    .setUpdateTime(new Date(currentTimeMillis))
                    .setCreateTime(new Date(currentTimeMillis));
            return bs;
        }).collect(Collectors.toList());

        List<BaseStockInfo> shNow = StockInfo.shBaseStockInfo().getData().getDiff().stream().map(a->{
            BaseStockInfo bs = new BaseStockInfo();
            bs.setCode(a.getF12()).setName(a.getF14()).setExchange("sh")
                    .setLastPrice(a.getF2()).setChange(a.getF3()).setChangeSixty(a.getF24()).setChangeBeginYear(a.getF25())
                    .setChangeAmount(a.getF4()).setTurnoverRate(a.getF8()).setPe(a.getF9()).setPb(a.getF23())
                    .setCirculationMarketValue(a.getF21()).setTotalValue(a.getF20())
                    .setIsExist(CommonStatus.NORMAL.getCode())
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

        /**  已写入的刷新指标信息及更新时间 */
        List<BaseStockInfo> szUpdate = getUpdate(szDataBase,szNow);
        List<BaseStockInfo> shUpdate = getUpdate(shDataBase,shNow);
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
    private List<BaseStockInfo> getUpdate(List<BaseStockInfo> dataBase, List<BaseStockInfo> now){
        List<BaseStockInfo> update = new ArrayList<>();
        dataBase.forEach(a->{
            int index = now.indexOf(a);
            if(index>0){
                BaseStockInfo bs = now.get(index);
                bs.setId(a.getId()).setCreateTime(a.getCreateTime());
                update.add(bs);
            }
        });
        return update;
    }
}
