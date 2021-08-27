package com.zwj.plus;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.Statement;

/**
 *  Mybatis 只支持针对 ParameterHandler、ResultSetHandler、StatementHandler、Executor 这4 种接口的插件
 *
 * "@Intercepts以及@Signature":配置需要拦截的对象，其中type是需要拦截的对象Class，method是对象里面的方法，args是方法参数类型。
 * 之后需要将插件注册，交给spring管理
 * @author zwj
 * @date 2021年08月27日
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query",args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = { Statement.class })})
@Component
public class TestPlus implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //执行业务逻辑之前的操作....
        //执行具体的业务逻辑
        invocation.proceed();
        // 执行业务逻辑之后的操作...
        return null;
    }
}
