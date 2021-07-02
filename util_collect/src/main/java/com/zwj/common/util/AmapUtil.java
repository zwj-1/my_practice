package com.zwj.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * 高德地图工具
 *
 * @author RingoChen
 */
public class AmapUtil {
    private static final String MAP_AK = "c8dcaae5644d67349e648c00acb38bc2";
    private static final String MAP_URL = "http://restapi.amap.com/v3/geocode/geo?output=JSON&key=" + MAP_AK;
    private static final String HTTPS_MAP_URL = "https://restapi.amap.com/v3/geocode/geo?output=JSON&key=" + MAP_AK;
    /**
     * 返回结果状态值 返回值为 0 或 1，0 表示请求失败；1 表示请求成功。
     */
    private static final String STATUS = "1";
    /**
     * 返回结果状态KEY
     */
    private static final String STATUS_KEY = "status";
    /**
     * 地理编码信息列表KEY
     */
    private static final String GEOCODES_KEY = "geocodes";


    /**
     * 地理编码(将地址解析成经纬度)
     *
     * @param address:地址
     * @return:
     */
    private static JSONObject getPosition(String address) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        // 异常返回结果字符串
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lat", 0);
        jsonObject.put("lng", 0);
        try {
            // 对参数进行UTF-8编码
            address = URLEncoder.encode(address, "UTF-8");
            // 创建远程url连接对象
            URL url = new URL(MAP_URL + "&address=" + address);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == HttpStatus.HTTP_OK) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuilder sbf = new StringBuilder();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
            return new JSONObject(result);
        } catch (Exception e) {
            return jsonObject;
        } finally {
            try {
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMapLocation(String address) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        JSONObject position = AmapUtil.getPosition(address);

        if (!STATUS.equals(position.getStr(STATUS_KEY))) {
            return "";
        }
        List<Object> list = (List<Object>) position.get(GEOCODES_KEY);
        if (CollUtil.isEmpty(list)) {
            return "";
        }
        cn.hutool.json.JSONObject geocodes = (cn.hutool.json.JSONObject) list.get(0);
        return geocodes.getStr("location");
    }

    public static void main(String[] args) {
        String address = "重庆市沙坪坝区高新区大学城景阳路35号光谷智创园A座东门三层";
        String s = getMapLocation(address);
        System.out.println(s);
    }
}
