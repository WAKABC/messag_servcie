package com.wak.entities.inventory;

import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/11/21 11:13
 * @Description TODO
 * @Version 1.0
 */

/**
 * inventory 库存表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_inventory_detail")
public class InventoryDetail {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * tx状态
     */
    @Column(name = "tx_status")
    private Byte txStatus;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 库存数量
     */
    @Column(name = "`count`")
    private Integer productCount;

    /**
     * 冻结数量
     */
    @Column(name = "deleted")
    private Byte deleted;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}