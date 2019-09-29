package com.vike.spider.stock.service.impl;


import com.vike.spider.stock.entity.Client;
import com.vike.spider.stock.repository.ClientRepository;
import com.vike.spider.stock.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Client login(String loginName, String password) {
        Optional<Client> optional = clientRepository.findClientByLoginName(loginName);
        Assert.isTrue(optional.isPresent(),"用户名或密码错误");
        Client client = optional.get();
        Assert.isTrue(client.getPassword().equals(password),"用户名或密码错误");
        return client;
    }
}
