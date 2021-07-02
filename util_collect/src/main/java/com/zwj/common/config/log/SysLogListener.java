package com.zwj.common.config.log;

import com.zwj.common.entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @ClassName：SysLogListener
 * @Description：注解形式的监听，异步监听系统日志事件
 * @Copyright：Copyright(c) 2019
 * @Company：中再云图科技有限公司
 * @Author：diaoy
 * @Date：2019/12/11
 **/
@Slf4j
@Component
public class SysLogListener {

   /* @Autowired
    private SysLogService sysLogService;

    *//**
     * 异步方式监听系统操作日志
     * @param event
     *//*
    @Async
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        // 保存日志
        sysLogService.save(sysLog);
        // 输出日志信息
        UtilSysLog.printOperateLog(sysLog);
    }*/
}
