package com.zwj.common.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @ClassName：UtilHttp
 * @Description：
 * @Copyright：Copyright(c) 2019
 * @Company：中再云图科技有限公司
 * @Author：Administrator
 * @Date：2019/12/12
 **/
public class UtilHttp {

    public static JSONObject getRequestJsonObject(HttpServletRequest request) throws IOException {
        String json = getRequestJsonString(request);
        return JSONObject.parseObject(json);
    }
    /***
     * 获取 request 中 json 字符串的内容
     *
     * @param request
     * @return : <code>byte[]</code>
     * @throws IOException
     */
    public static String getRequestJsonString(HttpServletRequest request)
            {
        String jsonData = "";
        String submitMehtod = request.getMethod();
        try {
            // GET
            if (submitMehtod.equals("GET")) {
                jsonData =  new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");
                // POST
            } else {
                jsonData =  getRequestPostStr(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        BufferedReader br = request.getReader();

        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
        return wholeStr;
    }
}
