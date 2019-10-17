package com.vike.spider.stock.service;

import com.vike.spider.common.PageLimit;
import com.vike.spider.stock.entity.Client;
import com.vike.spider.stock.entity.ClientMenu;
import org.springframework.data.domain.Page;

/**
 * @author: lsl
 * @createDate: 2019/9/24
 */
public interface ClientService {

    Client login(String loginName, String password);

    Page<Client> selectClient(PageLimit pageLimit);

    Page<ClientMenu> selectClientMenu(PageLimit pageLimit);

}
