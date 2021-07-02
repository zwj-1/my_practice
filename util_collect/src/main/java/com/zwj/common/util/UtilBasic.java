package com.zwj.common.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.XmlUtil;
import com.alibaba.fastjson.JSONArray;
import com.zwj.common.entity.FileVo;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/** 常用操作时间、字符串、数字转换
 * @author zwj
 * @date 2020-06-22
 */

public class UtilBasic {

    /**
     * 固定字符串长度 如：1 ---0001
     *
     * @param arg：参数
     * @return
     */
    public static String settledString(Integer arg, String pattern) {
        // 数字类字符固定位数 如：pattern="0000" 1 ---0001
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(arg);
    }

    /**
     * 将多为小数转换为自身需求的小数位
     *
     * @param value
     * @param pattern：如："#.00" "0.00%"
     * @param <T>              ：数据类型：Double、Interge、double、int等
     * @return
     */
    public static <T> String valueFormatString(T value, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * @param value:数值
     * @param num:小数位数
     * @param
     * @return
     */
    public static BigDecimal valueFormatBigDecimal(Double value, int num) {
        return new BigDecimal(value).setScale(num, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal valueFormatBigDecimal(Integer value, int num) {
        return new BigDecimal(value).setScale(num, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * LocalDateTime
     *
     * @param localDate ：localDate日期
     * @return
     */
    public static String localDateFormatStringDate(LocalDateTime localDate, String pattern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return fmt.format(localDate);
    }

    /**
     * localDate转String
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public static String localDateFormatStringDate(LocalDate localDate, String pattern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return fmt.format(localDate);
    }

    /**
     * String转localDate
     *
     * @param localDate:字符串日期
     * @return
     */
    public static LocalDate stringDateformatLocalDate(String localDate, String pattern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(localDate, fmt);
    }

    /**
     * String转Date
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date stringformatDate(String date, String pattern) {
        Date parseTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            parseTime = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseTime;
    }

    /**
     * Date转String
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateFormatString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    private static long getDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定时间月份天数
     *
     * @param time:"2021-02"
     * @return
     */
    public static long getDaysOfMonth(String time) {
        long days = 0L;
        if (StringUtils.isNotBlank(time)) {
            Date date = stringformatDate(time, "yyyy-MM");
            days = getDays(date);
        }
        return days;
    }

    /**
     * 获取指定时间月份天数
     *
     * @param date
     * @return
     */
    public static long getDaysOfMonth(Date date) {
        return getDays(date);
    }

    /**
     * 反射获取实体类字段
     *
     * @param t:实体类
     * @param fieldName:指定字段包含的字符串 如：attachmentIds
     * @return
     */
    public static <T> List<String> fieldReflect(T t, String fieldName) {
        List<String> fieldList = new ArrayList<>();
        try {
            Class cls = t.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);
                if (StringUtils.isBlank(fieldName)) {
                    fieldList.add((String) f.get(t));
                } else {
                    if (f.getName().contains(fieldName)) {
                        fieldList.add((String) f.get(t));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fieldList;
    }

    /**发起调用外部接口时，判断对象参数是否为空，不为空则url后拼接参数
     * 拼接url,(获取参数为null,则不拼接)
     * @param t
     * @param url
     * @param <T>
     * @return
     */
    public static <T> String findFieldAndValueReflect(T t, String url) {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        try {
            Class cls = t.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);

                Method m = t.getClass().getMethod(
                        "get" + getMethodName(f.getName()));
                Object val = m.invoke(t);
                if (ObjectUtil.isNotEmpty(val)) {
                    sb.append("&").append(f.getName()).append("=").append(val);
                }
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 把一个字符串的第一个字母大写、效率是最高的、
     *
     * @param fieldName：
     * @return
     */
    private static String getMethodName(String fieldName) {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    /**
     * 文件zip打包下载
     *
     * @param response
     * @param list：文件信息集合
     * @param downloadName：下载名称
     */
    public static void downloadZip(HttpServletResponse response, List<FileVo> list, String downloadName) {
        if (StringUtils.isBlank(downloadName)) {
            downloadName = "档案附件.zip";
        }

        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(downloadName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        OutputStream outputStream = null;
        ZipOutputStream zos = null;
        try {
            outputStream = response.getOutputStream();
            zos = new ZipOutputStream(outputStream);
            // 将文件流写入zip中
            //downloadToLocal(zos, list);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private static void downloadToLocal(ZipOutputStream zos, List<FileVo> list) throws IOException {
        HashMap<String, Integer> fileNameMap = new HashMap<>();
        for (FileVo fileVo : list) {
            //文件名称（带后缀）
            String fileName = fileVo.getFileName();
            InputStream is = null;
            BufferedInputStream in = null;
            byte[] buffer = new byte[1024];
            int len;
            //创建zip实体（一个文件对应一个ZipEntry）
            fileName = checkZipEntryName(fileNameMap, fileName);
            ZipEntry entry = new ZipEntry(fileName);
            try {
                //获取需要下载的文件流
//                String filePath = fileVo.getPath() + fileName;
                String filePath = fileVo.getPath();
                is = getFileInputStream(filePath);
                in = new BufferedInputStream(is);
                if (in == null) {
                    continue;
                }
                zos.putNextEntry(entry);
                while ((len = in.read(buffer)) != -1) {
                    zos.write(buffer, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    zos.closeEntry();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private static String checkZipEntryName(HashMap<String, Integer> map, String fileName) {
        if (map.containsKey(fileName)) {
            Integer num = map.get(fileName);
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String prefix = fileName.substring(0, fileName.lastIndexOf("."));
            fileName = prefix + "(" + num + ")" + suffix;
            map.put(fileName, num + 1);
        } else {
            map.put(fileName, 1);
        }
        return fileName;
    }

    /**
     * 远程获取服务器端文件输入流
     *
     * @param filepath
     * @return
     */
    private static InputStream getFileInputStream(String filepath) {
        InputStream inputStream = null;
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(filepath);
            urlConn = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            urlConn.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            urlConn.setDoInput(true);
            urlConn.setRequestMethod("GET");
            int responseCode = urlConn.getResponseCode();
            if (responseCode == 200) {
                // 从服务器返回一个输入流
                inputStream = urlConn.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 获取jar包所在路径：如：/usr/project/xygc/
     *
     * @return
     */
    public static String getRealPath() {
        final String proPackage1 = "zip";
        final String proPackage2 = "file";
        final String proPackage3 = "jar";
        //jar:file:/usr/project/hherp/huahuispring-1.0.0-alpha.jar!/BOOT-INF/classes!/
        // 获取jar包类路径
        String path = UtilBasic.class.getProtectionDomain().getCodeSource().getLocation().toString();
        // 对路径加工
        if (path.startsWith(proPackage1)) {
            //当class文件在war中时，此时返回zip:D:/...这样的路径
            path = path.substring(4);
        } else if (path.startsWith(proPackage2)) {
            //当class文件在class文件中时，此时返回file:/D:/...这样的路径  (本地启动)
            path = path.substring(6);
            path = path.substring(0, path.lastIndexOf("/"));
        } else if (path.startsWith(proPackage3)) {
            //当class文件在jar文件里面时，此时返回jar:file:/D:/...这样的路径  （linux启动）
            path = path.substring(9);
        }
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (path.contains(proPackage3)) {
            // 获取jar包所在目录
            path = path.substring(0, path.lastIndexOf("."));
            path = path.substring(0, path.lastIndexOf("/"));
        }
        return path;
    }

    /**
     * 将map数据转为xml字符串
     *
     * @param paramMap
     * @param xmlName
     * @return
     * @throws Exception
     */
    public static String mapToXml(Map<Object, Object> paramMap, String xmlName, String encoding) throws Exception {
        Document document = XmlUtil.mapToXml(paramMap, xmlName);
        return getXmlString(document, encoding);
    }

    /**
     * docment转为xml输出到文件
     *
     * @param document
     */
    private void documentOutPutXml(Document document) {
        File file = new File("C:/Users/zzyt/Desktop/person.xml");
        try {
            TransformerFactory formerFactory = TransformerFactory.newInstance();
            Transformer transformer = formerFactory.newTransformer();
            // 换行
            transformer.setOutputProperty(OutputKeys.INDENT, "YES");
            // 文档字符编码
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");

            // 可随意指定文件的后缀,效果一样,但xml比较好解析,比如: E:\\person.txt等
            transformer.transform(new DOMSource(document), new StreamResult(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将document转为xml字符串
     *
     * @param document
     * @return
     */
    private static String getXmlString(Document document, String encoding) {
        String xmlStr = "";
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            // 解决中文问题，试过用GBK不行
            //t.setOutputProperty("encoding", "GB23121");

            if (StringUtils.isNotBlank(encoding)) {
                t.setOutputProperty(OutputKeys.ENCODING, encoding);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            t.transform(new DOMSource(document), new StreamResult(bos));
            xmlStr = bos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlStr;
    }

    public static <T> List<T> objToList(Object obj, Class<T> cla) {
        List<T> list = new ArrayList<>();
        if (obj instanceof ArrayList<?> || obj instanceof JSONArray) {
            for (Object o : (List<?>) obj) {
                list.add(cla.cast(o));
            }
            return list;
        }
        return list;
    }

    public static void main(String[] args) {
        String realPath = getRealPath();
        System.out.println(realPath);
    }
}
