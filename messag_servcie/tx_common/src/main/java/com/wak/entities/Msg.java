package com.wak.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuankang
 * @date 2023/12/11 10:31
 * @Description TODO 独立消息服务消息对象
 * @Version 1.0
 */
@Table(name = "tx_msg")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 消息ID,例如交易订单号
     */
    @Column(name = "msg_id")
    private String msgId;

    /**
     * 消息状态 0=待发送 1=已完成
     */
    private Byte status;

    /**
     * 回调服务名
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 消息路由key
     */
    @Column(name = "routing_key")
    private String routingKey;

    /**
     * 消息内容
     */
    @Column(name = "json_msg")
    private String jsonMsg;

    /**
     * 删除标志，默认0不删除，1删除
     */
    private Byte deleted;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}
