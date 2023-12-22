-- auto Generated on 2023-11-17
-- DROP TABLE IF EXISTS inventory;
create database if not exists tx_inventory character set utf8mb4 collate utf8mb4_general_ci;
use tx_inventory;
create table if not exists tx_inventory
(
    id              int auto_increment comment '主键id'
        primary key,
    productId       int                                 null comment '产品编号',
    total_inventory int                                 null comment '总库存',
    lock_inventory  int                                 null comment '锁定库存',
    deleted         int                                 null comment '删除标志',
    create_time     timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     timestamp default CURRENT_TIMESTAMP not null comment '跟新时间'
)
    comment '库存表';

create table if not exists tx_inventory_detail
(
    id            int(10) auto_increment comment 'id'
        primary key,
    order_no      varchar(50)         default ''                not null comment '订单号',
    user_id       int(10)             default 0                 not null comment '用户id',
    product_id    int(10)             default 0                 not null comment '产品id',
    product_count int(10)             default 0                 not null comment '库存数量',
    deleted       tinyint(4) unsigned default 0                 not null comment '冻结数量',
    create_time   datetime            default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime            default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '库存详细表';

