package com.wak.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wuankang
 * @date 2023/12/11 10:36
 * @Description TODO
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgDTO implements Serializable {
    private Integer id;

    /**
     * 消息ID,例如交易订单号
     */
    private String msgId;

    /**
     * 消息状态 0=待发送 1=已完成
     */
    private Byte status;

    /**
     * 消息路由key
     */
    private String routingKey;

    /**
     * 应用程序名称
     */
    private String appName;

    /**
     * 消息内容
     */
    private String jsonMsg;
}
