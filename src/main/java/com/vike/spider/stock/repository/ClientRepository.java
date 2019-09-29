package com.vike.spider.stock.repository;

import com.vike.spider.stock.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: lsl
 * @createDate: 2019/9/23
 */
@Repository
public interface ClientRepository extends MongoRepository<Client,String> {

    Optional<Client> findClientByLoginName(String loginName);


}
