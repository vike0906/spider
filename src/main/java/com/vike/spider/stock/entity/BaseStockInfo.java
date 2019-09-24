package com.vike.spider.stock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author: lsl
 * @createDate: 2019/9/23
 */
@Document(collection = "baseStockInfo")
@CompoundIndexes({
        @CompoundIndex(name = "name_code",def = "{'code':1,'name':1}",unique = true)
})
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BaseStockInfo {

    @Id
    private String id;

    @Field("code")
    @Indexed()
    private String code;

    @Field("name")
    @Indexed()
    private String name;

    @Field("exchange")
    private String exchange;

    @Field("is_exist")
    private int isExist;

    @Field("update_time")
    private Date updateTime;

    @Field("create_time")
    private Date createTime;

    @Override
    public boolean equals(Object object){
        if(object instanceof BaseStockInfo){
            BaseStockInfo bs = (BaseStockInfo)object;
            return bs.getName().equals(this.name) && bs.getCode().equals(this.code);
        }
        return false;
    }
}
