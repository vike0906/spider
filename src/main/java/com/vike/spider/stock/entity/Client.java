package com.vike.spider.stock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author: lsl
 * @createDate: 2019/9/28
 */
@Document(collection = "client")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Client {
    @Id
    private String id;
    @Field("client_name")
    private String clientName;
    @Field("login_name")
    @Indexed(unique = true)
    private String loginName;
    private String password;
    private String salt;
    private Short status;
    @DBRef
    private List<ClientMenu> clientMenus;
    @Field("create_time")
    private Date createTime;
}
