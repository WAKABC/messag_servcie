drop database if exists tx_score;
create database if not exists tx_score;
use tx_score;

create table tx_score.tx_score
(
    id          int(10) auto_increment
        primary key,
    user_id     int(10)                             null comment '用户号',
    total_score int(10)                             null comment '总积分',
    lock_score  int(10)                             null comment '冻结积分',
    deleted     tinyint(1)                          null comment '删除标志',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '积分表';

create table tx_score.tx_score_detail
(
    id          int(10) auto_increment
        primary key,
    order_no    varchar(255)                        null comment '订单编号',
    tx_status   tinyint(1)                          null comment '订单状态',
    user_id     int(10)                             null comment '用户编号',
    score       int(10)                             null comment '积分',
    type        tinyint   default 0                 null comment '积分类型 0=消耗，1=新增',
    deleted     tinyint(1)                          null comment '删除标志',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '积分详细表';


