package com.vike.spider.stock.service.impl;


import com.vike.spider.common.PageLimit;
import com.vike.spider.stock.entity.Client;
import com.vike.spider.stock.entity.ClientMenu;
import com.vike.spider.stock.repository.ClientMenuRepository;
import com.vike.spider.stock.repository.ClientRepository;
import com.vike.spider.stock.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author: lsl
 * @createDate: 2019/9/24
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientMenuRepository clientMenuRepository;

    @Override
    public Client login(String loginName, String password) {
        Optional<Client> optional = clientRepository.findClientByLoginName(loginName);
        Assert.isTrue(optional.isPresent(),"用户名或密码错误");
        Client client = optional.get();
        Assert.isTrue(client.getPassword().equals(password),"用户名或密码错误");
        return client;
    }

    @Override
    public Page<Client> selectClient(PageLimit pageLimit) {
        Page<Client> page = clientRepository.findAll(pageLimit.page());
        return page;
    }

    @Override
    public Page<ClientMenu> selectClientMenu(PageLimit pageLimit) {
        Page<ClientMenu> page = clientMenuRepository.findAll(pageLimit.page());
        return page;
    }

}
