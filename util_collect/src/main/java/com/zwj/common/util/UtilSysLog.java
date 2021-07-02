package com.zwj.common.util;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.zwj.common.entity.SysLog;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName：UtilSysLog
 * @Description：系统日志工具类
 * @Copyright：Copyright(c) 2019
 * @Company：中再云图科技有限公司
 * @Author：diaoy
 * @Date：2019/12/11
 **/
public class UtilSysLog {
/*
    private static final String START_CHART = "at ";
    private static final String END_CHART = "\n";
    *//**
     * 自定义操作级别日志
     *//*
    final static Level OPERATE = Level.forName("operate", 350);

    final static Logger logger = LogManager.getLogger();


    *//**
     * 打印操作日志至控制台和文件
     * @param sysLog
     *//*
    public static void printOperateLog(SysLog sysLog) {
        logger.log(OPERATE, JSON.toJSONString(sysLog));
    }

    *//**
     * 生成系统日志对象
     * @param request
     * @param userInfo
     * @return
     *//*
    public static SysLog createSysLog(HttpServletRequest request, UserInfo userInfo,
                                      String description, String type) {
        // 开始时间
        long beginTime = Instant.now().toEpochMilli();
        SysLog sysLog = new SysLog();
        sysLog.setId(UUID.randomUUID().toString().replace("-",""));
        // 操作人信息
        sysLog.setUserId(userInfo.getUserId());
        sysLog.setUsername(userInfo.getUsername());
        // 接口地址
        sysLog.setActionUrl(URLUtil.getPath(request.getRequestURI()));
        // 开始时间
        sysLog.setStartTime(new Date());
        // 请求IP
        sysLog.setRequestIp(ServletUtil.getClientIP(request));
        // 请求方式 GET/POST
        sysLog.setRequestMethod(request.getMethod());
        //  浏览器
        sysLog.setUa(request.getHeader("user-agent"));
      *//*  //获取执行的方法名
        sysLog.setActionMethod(joinPoint.getSignature().getName());
        // 类路径
        sysLog.setClassPath(joinPoint.getTarget().getClass().getName());*//*
        // 结束时间
        sysLog.setFinishTime(new Date());
        // 参数
        sysLog.setParams(UtilHttp.getRequestJsonString(request));
        long endTime = Instant.now().toEpochMilli();
        // 花费时间
        sysLog.setConsumingTime(endTime - beginTime);
        // 注解上的操作内容
        sysLog.setDescription(description);
        // 获取日志注解的类型
        sysLog.setType(type);
        // 设置日志级别为operate
        sysLog.setLogLevel("operate");
        return sysLog;
    }

    *//**
     * 获取异常中的堆栈信息
     * @param ex
     * @return
     *//*
    public static String getStackTrace(Throwable ex) {
        StringBuffer stackTrace = new StringBuffer("");

        stackTrace.append(ex.toString() + END_CHART);
        stackTrace.append("----------------------------");
        //通过Throwable获得堆栈信息
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for(int i=0;i<stackElements.length;i++) {
                stackTrace.append(START_CHART + stackElements[i].toString() + END_CHART);
            }
        }
        return stackTrace.toString();
    }

    *//**
     * 获取自定义日志注解的描述
     * @param point
     * @return
     *//*
    public static String getDescription(JoinPoint point) {
        String descrption = "";
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        com.zwj.common.annotation.SysLog annotation = method.getAnnotation(com.zwj.common.annotation.SysLog.class);
        if (annotation != null) {
            descrption = annotation.description();
        }
        return descrption;
    }
    *//**
     * 获取自定义日志注解的类型
     * @param point
     * @return
     *//*
    public static String getType(JoinPoint point) {
        String type = "";
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        com.zwj.common.annotation.SysLog annotation = method.getAnnotation(com.zwj.common.annotation.SysLog.class);
        if (annotation != null) {
            type = annotation.type();
        }
        return type;
    }*/
}
