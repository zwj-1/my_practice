package com.zwj.common.config.log;

import com.zwj.common.entity.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @ClassName：OperateLogEvent
 * @Description：系统操作日志事件
 * @Copyright：Copyright(c) 2019
 * @Company：中再云图科技有限公司
 * @Author：diaoy
 * @Date：2019/12/11
 **/
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(SysLog source) {
        super(source);
    }
}
