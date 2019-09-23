package com.vike.spider.stock.repository;

import com.vike.spider.stock.entity.BaseStockInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * @author: lsl
 * @createDate: 2019/9/23
 */
@Service
public interface BaseStockInfoRepository extends MongoRepository<BaseStockInfo,String> {

    Optional<BaseStockInfo> findByCode(String code);

    Optional<BaseStockInfo> findByName(String name);

    List<BaseStockInfo> findAllByExchange(String exchange, Pageable pageable);

    List<BaseStockInfo> findAllByExchange(String exchange);
}
