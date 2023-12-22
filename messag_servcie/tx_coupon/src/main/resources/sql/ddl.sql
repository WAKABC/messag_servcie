create database if not exists tx_coupon character set utf8mb4 collate utf8mb4_general_ci;
use tx_coupon;
create table if not exists tx_coupon_consumer
(
    id                int(10) auto_increment
        primary key,
    user_id           int(10)                                       null comment '买家ID',
    coupon_receive_id int(10)                                       null comment '收到的优惠券id',
    order_no          varchar(50)                                   null comment '订单编号',
    deleted           tinyint(4) unsigned default 0                 not null comment '删除标志，默认0不删除，1删除',
    create_time       timestamp           default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time       timestamp           default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '优惠券消费记录表' charset = utf8;

create table if not exists tx_coupon_receive
(
    id           int(10) auto_increment
        primary key,
    user_id      int(10)                                       null comment '买家ID',
    coupon_id    int(10)                                       null comment '优惠券id',
    coupon_money int(10)                                       null comment '券额',
    status       tinyint             default 1                 null comment '状态，1为已领取未使用，2为已使用，3为已过期',
    expiry_date  int                                           null comment '有效期:(小时)',
    deleted      tinyint(4) unsigned default 0                 not null comment '删除标志，默认0不删除，1删除',
    create_time  timestamp           default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  timestamp           default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '优惠券领取记录表' charset = utf8;

