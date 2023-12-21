package com.wak.constant;

/**
 * @author wuankang
 * @date 2023/12/12 17:09
 * @Description TODO 常量
 * @Version 1.0
 */
public interface Constant {
    /**
     * Mq订单路由键（topic）
     */
    String MQ_ORDER_ROUTING_KEY = "topic_tx_order";

    /**
     * Mq积分路由键
     */
    String MQ_SCORE_ROUTING_KEY = "topic_tx_score";

    /**
     * Mq积分路由键
     */
    String MQ_INVENTORY_ROUTING_KEY = "topic_tx_inventory";

    /**
     * Mq积分路由键
     */
    String MQ_COUPON_ROUTING_KEY = "topic_tx_coupon";

    /**
     * xxl-job msg:过期时间 单位分钟
     */
    int VERDUE_TIME = 1;
}
