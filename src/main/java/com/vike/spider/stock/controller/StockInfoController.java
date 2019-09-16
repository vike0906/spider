package com.vike.spider.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: lsl
 * @createDate: 2019/9/16
 */
@Controller
@RequestMapping("stock-info")
public class StockInfoController {

    @GetMapping("hello")
    public String hello(){
        return "index";
    }

}
