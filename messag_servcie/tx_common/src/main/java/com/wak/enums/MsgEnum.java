package com.wak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuankang
 * @date 2023/12/12 17:07
 * @Description TODO
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum MsgEnum {
    /**
     * Not pay order status enum.
     */
    ORDER(1, "topic_tx_order", "order"),

    SCORE(2, "topic_tx_score", "score"),

    INVENTORY(3, "topic_tx_inventory", "inventory"),

    COUPON(4, "topic_tx_coupon", "coupon");

    /**
     * 代码
     */
    private int code;

    /**
     * 消息主题
     */
    private String msgTopic;

    /**
     * 描述
     */
    private String desc;
}
