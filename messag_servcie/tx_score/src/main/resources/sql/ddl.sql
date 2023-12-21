drop database if exists tx_score;
create database if not exists tx_score;
use tx_score;

DROP TABLE IF EXISTS tx_score;
CREATE TABLE tx_score
(
    `id`          int(10) NOT NULL AUTO_INCREMENT,
    `user_id`     INT(10) COMMENT '用户号',
    `total_score` INT(10) COMMENT '总积分',
    `lock_score`  INT(10) COMMENT '冻结积分',
    `deleted`     TINYINT(1) COMMENT '删除标志',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT = '积分表';


DROP TABLE IF EXISTS tx_score_detail;
CREATE TABLE tx_score_detail
(
    `id`          int(10) NOT NULL AUTO_INCREMENT,
    `order_no`    VARCHAR(255) COMMENT '订单编号',
    `tx_status`   TINYINT(1) COMMENT '订单状态',
    `user_id`     INT(10) COMMENT '用户编号',
    `score`       INT(10) COMMENT '积分',
    `deleted`     TINYINT(1) COMMENT '删除标志',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT = '积分详细表';
