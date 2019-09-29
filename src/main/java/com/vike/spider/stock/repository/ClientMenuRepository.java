package com.vike.spider.stock.repository;

import com.vike.spider.stock.entity.ClientMenu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * @author: lsl
 * @createDate: 2019/9/23
 */
@Repository
public interface ClientMenuRepository extends MongoRepository<ClientMenu,String> {


}
