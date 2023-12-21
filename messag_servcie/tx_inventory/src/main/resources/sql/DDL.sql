-- auto Generated on 2023-11-17
-- DROP TABLE IF EXISTS inventory;
create database if not exists tx_inventory character set utf8mb4 collate utf8mb4_general_ci;
CREATE TABLE if not exists tx_inventory.tx_inventory
(
    id          INT(10)             NOT NULL AUTO_INCREMENT COMMENT 'id',
    order_no    VARCHAR(50)         NOT NULL DEFAULT '' COMMENT '订单号',
    tx_status   TINYINT(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'tx状态',
    user_id     INT(10)             NOT NULL DEFAULT 0 COMMENT '用户id',
    product_id  INT(10)             NOT NULL DEFAULT 0 COMMENT '产品id',
    `count`     INT(10)             NOT NULL DEFAULT 0 COMMENT '库存数量',
    deleted     TINYINT(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志，默认0不删除，1删除',
    create_time DATETIME            NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT current_timestamp COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT 'inventory 库存消耗明细信息表';
