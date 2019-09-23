package com.vike.spider.stock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
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
public class BaseStockInfo {

    @Field("code")
    @Indexed()
    private String code;

    @Field("name")
    @Indexed()
    private String name;

    @Field("exchange")
    private String exchange;

    @Field("update_time")
    private Date updateTime;
}
