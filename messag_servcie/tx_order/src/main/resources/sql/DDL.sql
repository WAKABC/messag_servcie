create database if not exists tx_order character set utf8mb4;
CREATE TABLE if not exists tx_order
(
    id                int(10)             NOT NULL AUTO_INCREMENT,
    user_id           int(10)                      DEFAULT '0' COMMENT '用户账号ID',
    product_id        int(10)             NOT NULL DEFAULT '0' COMMENT '产品ID',
    product_count     int(4)                       DEFAULT '0' COMMENT '购买数量',
    order_no          varchar(50)         NOT NULL COMMENT '订单号',
    status tinyint (4) DEFAULT '0' COMMENT '订单状态：0=已拍下，1=拍下失败，2=未支付，3=支付成功，4=支付失败',
    total_money       decimal(10, 0)               DEFAULT '0' COMMENT '总价',
    coupon_receive_id int(10)                      DEFAULT NULL COMMENT '优惠券id',
    district_money    decimal(8, 2)       NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
    payment_money     decimal(8, 2)       NOT NULL DEFAULT '0.00' COMMENT '支付金额',
    decrease_score    int(10)                      DEFAULT '0' COMMENT '扣除积分=淘金币积分',
    largess_score     int(10)                      DEFAULT '0' COMMENT '赠送积分',
    deleted           tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志，默认0不删除，1删除',
    create_time       timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time       timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id)
)engine=innodb auto_increment=4 default charset=utf8 COMMENT='订单表';

