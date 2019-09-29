package com.vike.spider.common;

import com.vike.spider.stock.entity.ClientMenu;

import java.util.*;

/**
 * @author: lsl
 * @createDate: 2019/9/28
 */
public class Common {

    public static List<ClientMenu> parse(List<ClientMenu> clientMenus){
        return parse(clientMenus,"");
    }

    public static List<ClientMenu> parse(List<ClientMenu> clientMenus, String menuName){
        String parentId = "";
        clientMenus.sort(Comparator.comparingInt(ClientMenu::getSort));
        List<ClientMenu> parentMenus = new ArrayList<>();
        Map<String,List<ClientMenu>> map = new HashMap<>();

        for(ClientMenu clientMenu:clientMenus){
            if(clientMenu.getIsParent()==2) {
                if (menuName.equals(clientMenu.getName())) {
                    parentId = clientMenu.getParentId();
                    clientMenu.setIsActive((short) 1);
                } else {
                    clientMenu.setIsActive((short) 0);
                }
                if(map.get(clientMenu.getParentId())==null){
                    List<ClientMenu> list = new ArrayList<>();
                    list.add(clientMenu);
                    map.put(clientMenu.getParentId(),list);
                }else {
                    map.get(clientMenu.getParentId()).add(clientMenu);
                }
            }
        }


        for(ClientMenu clientMenu:clientMenus){
            if(clientMenu.getIsParent()==1) {
                if(parentId.equals(clientMenu.getId())){
                    clientMenu.setIsActive((short)1);
                }else{
                    clientMenu.setIsActive((short)0);
                }
                clientMenu.setClientMenus(map.get(clientMenu.getId()));
                parentMenus.add(clientMenu);
            }
        }
        return parentMenus;

    }

}
