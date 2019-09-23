package com.vike.spider.stock.spider.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: lsl
 * @createDate: 2019/9/23
 */
@Data
@NoArgsConstructor
public class DataInfo {
    private int total;
    private List<Base> diff;
}
