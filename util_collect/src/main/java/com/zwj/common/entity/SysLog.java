package com.zwj.common.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title:日志信息 </p>
 * <p>Description:日志信息 </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company:中国再生资源 </p>
 * @author ${USER}
 * @Date  2020-01-06
 * @version 1.0
 */
@Data
@ApiModel("日志信息")
@TableName("t_sys_log")
public class SysLog implements Serializable  {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     * id：varchar(32) ==》 id：String
     */
    @ApiModelProperty(value = "ID")
        @TableId(value = "id", type = IdType.UUID)
        private String id;
    /**
     * 操作人ID
     * user_id：varchar(64) ==》 userId：String
     */
    @ApiModelProperty(value = "操作人ID")
    private String userId;
    /**
     * 操作人
     * username：varchar(255) ==》 username：String
     */
    @ApiModelProperty(value = "操作人")
    private String username;
    /**
     * 操作IP
     * request_ip：varchar(5000) ==》 requestIp：String
     */
    @ApiModelProperty(value = "操作IP")
    private String requestIp;
    /**
     * 日志类型（1 操作日志、2异常日志）
     * type：varchar(16) ==》 type：String
     */
    @ApiModelProperty(value = "日志类型（1 操作日志、2异常日志）")
    private String type;
    /**
     * 操作描述
     * description：varchar(2000) ==》 description：String
     */
    @ApiModelProperty(value = "操作描述")
    private String description;
    /**
     * 请求方法
     * action_method：varchar(256) ==》 actionMethod：String
     */
    @ApiModelProperty(value = "请求方法")
    private String actionMethod;
    /**
     * 返回参数(json格式)
     * response_data：mediumtext ==》 responseData：String
     */
    @ApiModelProperty(value = "返回参数(json格式)")
    private String responseData;
    /**
     * 请求URL
     * action_url：text ==》 actionUrl：String
     */
    @ApiModelProperty(value = "请求URL")
    private String actionUrl;
    /**
     * 请求参数
     * params：text ==》 params：String
     */
    @ApiModelProperty(value = "请求参数")
    private String params;
    /**
     * 浏览器信息
     * ua：varchar(255) ==》 ua：String
     */
    @ApiModelProperty(value = "浏览器信息")
    private String ua;
    /**
     * 类路径
     * class_path：varchar(2000) ==》 classPath：String
     */
    @ApiModelProperty(value = "类路径")
    private String classPath;
    /**
     * 请求方法
     * request_method：varchar(16) ==》 requestMethod：String
     */
    @ApiModelProperty(value = "请求方法")
    private String requestMethod;
    /**
     * 开始时间
     * start_time：datetime ==》 startTime：Date
     */
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    /**
     * 结束时间
     * finish_time：datetime ==》 finishTime：Date
     */
    @ApiModelProperty(value = "结束时间")
    private Date finishTime;
    /**
     * 消耗时间
     * consuming_time：bigint(20) ==》 consumingTime：Long
     */
    @ApiModelProperty(value = "消耗时间")
    private Long consumingTime;
    /**
     * 异常详情信息 堆栈信息
     * ex_detail：mediumtext ==》 exDetail：String
     */
    @ApiModelProperty(value = "异常详情信息 堆栈信息")
    private String exDetail;
    /**
     * 异常描述 e.getMessage
     * ex_desc：mediumtext ==》 exDesc：String
     */
    @ApiModelProperty(value = "异常描述 e.getMessage")
    private String exDesc;
    /**
     * 日志级别
     * log_level：varchar(16) ==》 logLevel：String
     */
    @ApiModelProperty(value = "日志级别")
    private String logLevel;



}
