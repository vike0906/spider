package com.vike.spider.stock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author: lsl
 * @createDate: 2019/9/28
 */
@Document(collection = "client_menu")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ClientMenu {
    @Id
    private String id;
    private String name;
    private String url;
    private Short sort;
    @Field("is_parent")
    private Short isParent;
    private String parentId;
    private Short status;
    @Field("create_time")
    private Date createTime;
    @Transient
    private List<ClientMenu> clientMenus;
    @Transient
    private Short isActive;
}
