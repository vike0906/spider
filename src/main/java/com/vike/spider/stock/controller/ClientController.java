package com.vike.spider.stock.controller;

import com.vike.spider.common.Common;
import com.vike.spider.common.ExceptionEnum;
import com.vike.spider.common.PageLimit;
import com.vike.spider.stock.entity.BaseStockInfo;
import com.vike.spider.stock.entity.Client;
import com.vike.spider.stock.entity.ClientMenu;
import com.vike.spider.stock.repository.ClientMenuRepository;
import com.vike.spider.stock.repository.ClientRepository;
import com.vike.spider.stock.service.ClientService;
import com.vike.spider.stock.service.StockInfoService;
import com.vike.spider.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author: lsl
 * @createDate: 2019/9/28
 */
@Controller
@RequestMapping("client")
public class ClientController {

    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientMenuRepository clientMenuRepository;
    @Autowired
    StockInfoService stockInfoService;

    /** 登录 */
    @RequestMapping("login")
    public String login(ModelMap modelMap,String loginName,String password){

        Optional<Client> optional = clientRepository.findClientByLoginName(loginName);

        if(optional.isPresent()){

            Client client = optional.get();

            String pwd = EncryptUtils.MD5(password+client.getSalt());

            if(pwd.equals(client.getPassword())){
                List<ClientMenu> clientMenus = Common.parse(client.getClientMenus(),"大盘汇总");
                client.setClientMenus(clientMenus);
                modelMap.addAttribute("client",client);
                Page<BaseStockInfo> page = stockInfoService.selectBaseStockInfo(new PageLimit(), "change");
                modelMap.addAttribute("page",page);
                return "stock/index";
            }else {
                modelMap.addAttribute("message", ExceptionEnum.LOGIN_ERROR.getMessage());
                return "login";
            }
        }else {
            modelMap.addAttribute("message", ExceptionEnum.LOGIN_ERROR.getMessage());
            return "login";
        }
    }

    @GetMapping("insert")
    @ResponseBody
    public String insert(){
        List<ClientMenu> clientMenus = new ArrayList<>(6);
        ClientMenu clientMenu1 = new ClientMenu();
        ClientMenu clientMenu2 = new ClientMenu();
        ClientMenu clientMenu11 = new ClientMenu();
        ClientMenu clientMenu12 = new ClientMenu();
        ClientMenu clientMenu21 = new ClientMenu();
        ClientMenu clientMenu22 = new ClientMenu();
        clientMenu1.setId("1").setName("股市信息").setSort((short)1).setIsParent((short)1).setParentId("0").setStatus((short)1).setCreateTime(new Date());
        clientMenu2.setId("2").setName("系统管理").setSort((short)2).setIsParent((short)1).setParentId("0").setStatus((short)1).setCreateTime(new Date());
        clientMenu11.setId("3").setName("大盘汇总").setSort((short)1).setUrl("stock/base").setIsParent((short)2).setParentId("1").setStatus((short)1).setCreateTime(new Date());
        clientMenu12.setId("4").setName("实时公告").setSort((short)2).setUrl("stock/notice").setIsParent((short)2).setParentId("1").setStatus((short)1).setCreateTime(new Date());
        clientMenu21.setId("5").setName("用户管理").setSort((short)1).setUrl("client/user").setIsParent((short)2).setParentId("2").setStatus((short)1).setCreateTime(new Date());
        clientMenu22.setId("6").setName("权限管理").setSort((short)2).setUrl("client/permission").setIsParent((short)2).setParentId("2").setStatus((short)1).setCreateTime(new Date());
        clientMenus.add(clientMenu1);
        clientMenus.add(clientMenu2);
        clientMenus.add(clientMenu11);
        clientMenus.add(clientMenu12);
        clientMenus.add(clientMenu21);
        clientMenus.add(clientMenu22);
        clientMenuRepository.saveAll(clientMenus);

        Client client = new Client();
        client.setId("1").setClientName("vike0906").setLoginName("admin").setPassword("207acd61a3c1bd506d7e9a4535359f8a").setSalt("salt").setStatus((short)1).setClientMenus(clientMenus).setCreateTime(new Date());
        clientRepository.save(client);

        return "success";
    }


}
