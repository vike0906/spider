package com.vike.spider.stock.service.impl;

import com.vike.spider.common.PageLimit;
import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.repository.BaseStockInfoRepository;
import com.vike.spider.stock.service.StockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Page<BaseStockInfo> selectBaseStockInfo(PageLimit pageLimit, String order) {
        Page<BaseStockInfo> page = baseStockInfoRepository.findAll(pageLimit.page(true,order));
        return page;
    }
}
