package com.wak.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @version 1.0.0
 * @date 2023/11/21
 * @description TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_inventory")
public class Inventory implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 产品编号
     */
    @Column(name = "productId")
    private Integer productid;

    /**
     * 总库存
     */
    @Column(name = "total_inventory")
    private Integer totalInventory;

    /**
     * 锁定库存
     */
    @Column(name = "lock_inventory")
    private Integer lockInventory;

    /**
     * 删除标志
     */
    @Column(name = "deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 跟新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}