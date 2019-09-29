package com.vike.spider.stock.service;

import com.vike.spider.common.PageLimit;
import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.entity.Client;
import org.springframework.data.domain.Page;

/**
 * @author: lsl
 * @createDate: 2019/9/24
 */
public interface ClientService {

    Client login(String loginName, String password);

}
