-- auto Generated on 2023-12-11
-- DROP TABLE IF EXISTS tx_msg;
create database if NOT EXISTS tx_msg;
use tx_msg;
CREATE TABLE tx_msg
(
    id          INT(10)       NOT NULL AUTO_INCREMENT COMMENT 'id',
    msg_id      VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '消息ID,例如交易订单号',
    `status`    TINYINT(4)    NOT NULL DEFAULT 0 COMMENT '消息状态 0=待发送 1=已发送',
    app_name    VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '回调服务名',
    routing_key VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '消息路由key',
    json_msg    VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '消息内容',
    deleted     TINYINT(4)    NOT NULL DEFAULT 0 COMMENT '删除标志，默认0不删除，1删除',
    create_time TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '消息表';