package com.vike.spider.security;

import com.vike.spider.stock.entity.ClientMenu;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;


/**
 * @author: lsl
 * @createDate: 2019/9/29
 */
public class ClientDetail extends User {


    private List<ClientMenu> clientMenus;

    private String clientName;

    public ClientDetail(String username, String password, Collection<? extends GrantedAuthority> authorities,List<ClientMenu> clientMenus,String clientName) {
        super(username, password, authorities);
        this.clientMenus = clientMenus;
        this.clientName = clientName;
    }

    public List<ClientMenu> getClientMenus() {
        return clientMenus;
    }

    public void setClientMenus(List<ClientMenu> clientMenus) {
        this.clientMenus = clientMenus;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
