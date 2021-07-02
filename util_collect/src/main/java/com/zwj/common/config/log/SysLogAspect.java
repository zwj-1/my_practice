package com.zwj.common.config.log;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.zwj.common.entity.SysLog;
import com.zwj.common.util.UtilSysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @ClassName：OperateLogAspect
 * @Description：系统操作日志切面, ①切面注解得到请求数据 -> ②发布监听事件 -> ③异步监听日志入库
 * @Copyright：Copyright(c) 2019
 * @Company：中再云图科技有限公司
 * @Author：diaoy
 * @Date：2019/12/11
 **/
@Slf4j
@Aspect
@Component
public class SysLogAspect {

   /* private SysLog sysLog = new SysLog();
    *//**
     * 事件发布是由ApplicationContext对象管控的，我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布
     **//*
    @Autowired
    private ApplicationContext applicationContext;

    *//***
     * 定义controller切入点拦截规则，拦截SysLog注解的方法
     *//*
    @Pointcut("@annotation(com.zwj.common.annotation.SysLog)")
    public void sysLogAspect() {

    }

    *//***
     * 拦截控制层的操作日志
     * @param joinPoint
     * @return
     * @throws Throwable
     *//*
    @Before(value = "sysLogAspect()")
    public void recordLog(JoinPoint joinPoint) throws Throwable {
        // 开始时间
        long beginTime = Instant.now().toEpochMilli();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        sysLog.setId(UUID.randomUUID().toString().replace("-",""));
        // 操作人信息
        UserInfo userInfo = UserContext.current();
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
        //访问目标方法的参数 可动态改变参数值
        Object[] args = joinPoint.getArgs();
        //获取执行的方法名
        sysLog.setActionMethod(joinPoint.getSignature().getName());
        // 类路径
        sysLog.setClassPath(joinPoint.getTarget().getClass().getName());
        // 结束时间
        sysLog.setFinishTime(new Date());
        // 参数
        sysLog.setParams(Arrays.toString(args));
        long endTime = Instant.now().toEpochMilli();
        // 花费时间
        sysLog.setConsumingTime(endTime - beginTime);
        // 注解上的操作内容
        sysLog.setDescription(UtilSysLog.getDescription(joinPoint));
        // 获取日志注解的类型
        sysLog.setType(UtilSysLog.getType(joinPoint));
    }

    *//**
     * 返回通知
     * @param ret
     * @throws Throwable
     *//*
    @AfterReturning(returning = "ret", pointcut = "sysLogAspect()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        RestResult result = Convert.convert(RestResult.class, ret);
        sysLog.setResponseData(JSON.toJSONString(result));
        if (result.getCode() == 200){
            // 正常返回
            sysLog.setLogLevel("operate");
        } else {
            // 非正常返回
//            sysLog.setType(YTSystemConstant.LOG_TYPE.UNUSUAL);
            sysLog.setExDetail(JSON.toJSONString(result));
            sysLog.setExDesc(result.getMessage());
        }
        // 发布事件
        applicationContext.publishEvent(new SysLogEvent(sysLog));
    }

    *//**
     * 异常通知
     * @param e
     *//*
    @AfterThrowing(pointcut = "sysLogAspect()",throwing = "e")
    public void doAfterThrowable(Throwable e){
        // 异常
//        sysLog.setType(YTSystemConstant.LOG_TYPE.ERROR);
        // 异常对象
        sysLog.setExDetail(UtilSysLog.getStackTrace(e));
        // 异常信息
        sysLog.setExDesc(e.getMessage());
        sysLog.setLogLevel("error");
        sysLog.setId(null);
        // 发布事件
        applicationContext.publishEvent(new SysLogEvent(sysLog));
    }*/
}
