package com.vike.spider.stock.controller;

import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.repository.BaseStockInfoRepository;
import com.vike.spider.stock.spider.StockInfo;
import com.vike.spider.stock.spider.entity.DFBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lsl
 * @createDate: 2019/9/16
 */
@Controller
@RequestMapping("stock-info")
public class StockInfoController {

    @Autowired
    BaseStockInfoRepository baseStockInfoRepository;

    @GetMapping("hello")
    public String hello(){
        return "index";
    }

    @GetMapping("insert")
    @ResponseBody
    public String insert(){
        DFBase dfBase = StockInfo.shBaseStockInfo();
        List<BaseStockInfo> sz = dfBase.getData().getDiff().stream().map(a -> {
            BaseStockInfo bs = new BaseStockInfo();
            bs.setCode(a.getF12());
            bs.setName(a.getF14());
            bs.setExchange("sh");
            bs.setUpdateTime(new Date(System.currentTimeMillis()));
            return bs;
        }).collect(Collectors.toList());
        baseStockInfoRepository.saveAll(sz);
        return "success";
    }

}
