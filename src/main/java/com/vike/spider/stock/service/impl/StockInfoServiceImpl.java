package com.vike.spider.stock.service.impl;

import com.vike.spider.common.PageLimit;
import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.repository.BaseStockInfoRepository;
import com.vike.spider.stock.service.StockInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author: lsl
 * @createDate: 2019/9/24
 */
@Service
public class StockInfoServiceImpl implements StockInfoService {

    @Autowired
    BaseStockInfoRepository baseStockInfoRepository;

    @Override
    public Page<BaseStockInfo> selectBaseStockInfo(String exchange, PageLimit pageLimit, String order) {
        BaseStockInfo baseStockInfo = new BaseStockInfo();
        if(StringUtils.isNotEmpty(exchange)){
            baseStockInfo.setExchange(exchange);
        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("lastPrice", "change", "changeSixty", "changeBeginYear", "changeAmount",
                "turnoverRate", "pe", "pb", "circulationMarketValue", "totalValue", "isExist");
        Example<BaseStockInfo> example=Example.of(baseStockInfo,exampleMatcher);
        Page<BaseStockInfo> page = baseStockInfoRepository.findAll(example,pageLimit.page(true,order));
        return page;
    }
}
