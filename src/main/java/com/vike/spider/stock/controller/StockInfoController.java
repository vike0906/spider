package com.vike.spider.stock.controller;

import com.vike.spider.common.PageLimit;
import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.repository.BaseStockInfoRepository;
import com.vike.spider.stock.service.StockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: lsl
 * @createDate: 2019/9/16
 */
@Controller
@RequestMapping("stock-info")
public class StockInfoController {

    @Autowired
    BaseStockInfoRepository baseStockInfoRepository;
    @Autowired
    StockInfoService stockInfoService;

    @GetMapping("base")
    public String base(ModelMap map, PageLimit pageLimit, @RequestParam(value = "order",defaultValue = "change") String order){
        Page<BaseStockInfo> page = stockInfoService.selectBaseStockInfo(pageLimit, order);
        map.addAttribute("page",page);
        map.addAttribute("userName","李华");
        return "stock/index";

    }

    @GetMapping("hello")
    public String hello(){
        return "index";
    }

    /**@GetMapping("insert")
    @ResponseBody
    public String insert(){
        long currentTimeMillis = System.currentTimeMillis();

        DFBase dfBase1 = StockInfo.szBaseStockInfo();
        List<BaseStockInfo> sz = dfBase2BaseStockInfo(dfBase1,"sz",currentTimeMillis);
        baseStockInfoRepository.saveAll(sz);

        DFBase dfBase2 = StockInfo.shBaseStockInfo();
        List<BaseStockInfo> sh = dfBase2BaseStockInfo(dfBase2,"sh",currentTimeMillis);
        baseStockInfoRepository.saveAll(sh);
        return "success";
    }

    private List<BaseStockInfo> dfBase2BaseStockInfo(DFBase dfBase,String exchange, long currentTimeMillis){
        List<BaseStockInfo> baseStockInfos = dfBase.getData().getDiff().stream().map(a -> {
            BaseStockInfo bs = new BaseStockInfo();
            bs.setCode(a.getF12());
            bs.setName(a.getF14());
            bs.setExchange(exchange);
            bs.setIsExist(1);
            bs.setCreateTime(new Date(currentTimeMillis));
            bs.setUpdateTime(new Date(currentTimeMillis));
            return bs;
        }).collect(Collectors.toList());
        return baseStockInfos;
    }*/

}
