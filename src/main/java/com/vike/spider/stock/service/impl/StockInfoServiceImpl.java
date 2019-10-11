package com.vike.spider.stock.service.impl;

import com.vike.spider.common.PageLimit;
import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.repository.BaseStockInfoRepository;
import com.vike.spider.stock.service.StockInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lsl
 * @createDate: 2019/9/24
 */
@Service
public class StockInfoServiceImpl implements StockInfoService {

    @Autowired
    BaseStockInfoRepository baseStockInfoRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<BaseStockInfo> selectBaseStockInfo(String exchange, String queryStr, PageLimit pageLimit, String order) {

        Query query = new Query();

        if(StringUtils.isNotBlank(exchange)){
            Criteria criteria1 = Criteria.where("exchange").is(exchange);
            query.addCriteria(criteria1);
        }

        if(StringUtils.isNotBlank(queryStr)){
            String a = "^.*";
            String b = ".*$";
            queryStr = a+queryStr+b;
            Criteria criteria2 = Criteria.where("")
                    .orOperator(
                            Criteria.where("code").regex(queryStr),
                            Criteria.where("name").regex(queryStr));
            query.addCriteria(criteria2);
        }

        long count = mongoTemplate.count(query, BaseStockInfo.class);

        PageRequest pq = pageLimit.page(true, order);
        query.with(pq);
        List<BaseStockInfo> baseStockInfos = mongoTemplate.find(query, BaseStockInfo.class);
        Page<BaseStockInfo> page = new PageImpl<>(baseStockInfos, pq, count);
        return page;
    }



    /**
     BaseStockInfo baseStockInfo = new BaseStockInfo();
     if(StringUtils.isNotEmpty(exchange)){
     baseStockInfo.setExchange(exchange);
     }
     ExampleMatcher exampleMatcher = ExampleMatcher.matching()
     .withIgnorePaths("lastPrice", "change", "changeSixty", "changeBeginYear", "changeAmount",
     "turnoverRate", "pe", "pb", "circulationMarketValue", "totalValue", "isExist");
     if(StringUtils.isNotEmpty(queryStr)){
     exampleMatcher.withMatcher("code", ExampleMatcher.GenericPropertyMatcher.of())
     }
     Example<BaseStockInfo> example=Example.of(baseStockInfo,exampleMatcher);
     Page<BaseStockInfo> page = baseStockInfoRepository.findAll(example,pageLimit.page(true,order));
     */
}
