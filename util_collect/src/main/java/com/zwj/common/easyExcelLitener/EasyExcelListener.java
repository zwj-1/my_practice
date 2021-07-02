package com.zwj.common.easyExcelLitener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 自己按官网开发文档编写
 *
 * @author zwj
 * @date 2021年07月01日
 */
@Slf4j
public class EasyExcelListener<T> extends AnalysisEventListener<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelListener.class);
    private T t;
    private List<T> list = new ArrayList<>(16);

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data:excel对象
     * @param context：文件内容上下文
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("所有数据解析完成！");
    }
}
