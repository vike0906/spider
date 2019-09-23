package com.vike.spider.stock.spider;

import com.google.gson.Gson;
import com.vike.spider.stock.spider.entity.DFBase;
import com.vike.spider.stock.utils.http.HttpClient;
import org.springframework.stereotype.Component;


/**
 * @author: lsl
 * @createDate: 2019/9/23
 */
@Component
public class StockInfo {

    private final static String BASE_URL="http://43.push2.eastmoney.com/api/qt/clist/get?" +
            "cb=stock-info" +
            "&pn=1" +
            "&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152"+
            "&np=1";
    private static final String SZ_EXCHANGE =  "m:0+t:6,m:0+t:13,m:0+t:80";
    private static final String SH_EXCHANGE =  "m:1+t:2,m:1+t:23";
    private static final String ALL_EXCHANGE =  "m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23";
    private static final String SORT_STOCK_CODE =  "f12";






    /**
     * @description: TODO 获取A股股票信息，
     * @params [exchange, sort, number] sort=f12:按股票代码排序 sort=f14:按股票名称排序
     * @return void
     */
    public static DFBase baseStockInfo(String exchange, String sort, int number){

        StringBuffer apiUrl = new StringBuffer(BASE_URL);

        apiUrl.append("&fid=");
        apiUrl.append(sort);
        apiUrl.append("&fs=");
        apiUrl.append(exchange);
        apiUrl.append("&pz=");
        apiUrl.append(number);
        System.out.println(apiUrl.toString());
        String response = HttpClient.doGet(apiUrl.toString());

        response = response.replace("stock-info(","");
        response = response.replace(");","");
        Gson gson = new Gson();

        DFBase dfBase = gson.fromJson(response, DFBase.class);

        return dfBase;
    }

    /**获取全部深交所股票*/
    public static DFBase szBaseStockInfo(){
        DFBase base01 = baseStockInfo(SZ_EXCHANGE, SORT_STOCK_CODE, 1);
        int total = base01.getData().getTotal();
        /** 全部深交所股票 */
        DFBase base02 = baseStockInfo(SZ_EXCHANGE, SORT_STOCK_CODE, total);
        return base02;
    }

    /**获取全部上交所股票*/
    public static DFBase shBaseStockInfo(){
        DFBase base01 = baseStockInfo(SH_EXCHANGE, SORT_STOCK_CODE, 1);
        int total = base01.getData().getTotal();
        /** 全部上交所股票 */
        DFBase base02 = baseStockInfo(SH_EXCHANGE, SORT_STOCK_CODE, total);
        return base02;
    }

    public static void main(String [] args){
        szBaseStockInfo();
        shBaseStockInfo();
        System.out.println("完成");
    }
}
