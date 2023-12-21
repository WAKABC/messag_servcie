-- auto Generated on 2023-12-10
-- DROP TABLE IF EXISTS tx_pay;
create database tx_pay;
use tx_pay;
CREATE TABLE tx_pay
(
    id          INT(11)       NOT NULL AUTO_INCREMENT COMMENT 'id',
    pay_no      VARCHAR(50)   NOT NULL COMMENT '交易单号',
    order_no    VARCHAR(50)   NOT NULL COMMENT '订单号',
    user_id     INT(10)       NOT NULL DEFAULT 0 COMMENT '用户账号ID',
    amount      DECIMAL(8, 2) NOT NULL DEFAULT 0.00 COMMENT '交易金额',
    pay_state   TINYINT(4)    NOT NULL DEFAULT 0 COMMENT '支付类型 0:余额 1:微信 2:支付宝 3:xxx',
    `status`    TINYINT(4)    NOT NULL DEFAULT 0 COMMENT '支付状态 -1：取消 0 未完成 1已完成 -2:异常',
    deleted     TINYINT(4)    NOT NULL DEFAULT 0 COMMENT '删除标志，默认0不删除，1删除',
    create_time TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '支付交易表';
