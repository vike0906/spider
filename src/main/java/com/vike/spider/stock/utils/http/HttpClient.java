package com.vike.spider.stock.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author: lsl
 * @createDate: 2019/9/23
 * TODO 网络请求工具
 */
public class HttpClient {

    /** 设置连接主机服务器的超时时间：15000毫秒 */
    private static final int CONNECT_OUT_TIME = 15000;
    /** 设置读取远程返回的数据时间：60000毫秒 */
    private static final int RESPONSE_OUT_TIME = 60000;



    /**
     * @description: TODO 处理GET请求
     * @params [httpUrl]
     * @return java.lang.String
     */
    public static String doGet(String httpUrl) {

        HttpURLConnection connection = null;
        String result = null;// 返回结果字符串
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            /** 设置连接方式：get */
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECT_OUT_TIME);
            connection.setReadTimeout(RESPONSE_OUT_TIME);
            /** 发送请求 */
            connection.connect();
            if (connection.getResponseCode() == 200) {
                result = readResponse(connection);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return result;
    }

    /**
     * @description: TODO 处理POST请求
     * @params [httpUrl, param]
     * @return java.lang.String
     */
    public static String doPost(String httpUrl, String param) {

        HttpURLConnection connection = null;
        OutputStream os = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(CONNECT_OUT_TIME);
            connection.setReadTimeout(RESPONSE_OUT_TIME);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            os = connection.getOutputStream();
            os.write(param.getBytes());
            if (connection.getResponseCode() == 200) {
                result = readResponse(connection);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return result;
    }

    /**
     * @description: TODO 通过连接对象获取一个输入流，向远程读取
     * @params [connection]
     * @return java.lang.String
     */
    private static String readResponse(HttpURLConnection connection){
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer sbf = new StringBuffer();
        try {
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            sbf = new StringBuffer();
            String temp;
            while ((temp = br.readLine()) != null) {
                sbf.append(temp);
                sbf.append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sbf.toString();
    }
}
