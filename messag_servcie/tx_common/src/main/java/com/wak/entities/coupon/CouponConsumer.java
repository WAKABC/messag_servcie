package com.wak.entities.coupon;

import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/11/17 16:59
 * @Description TODO 优惠券消费记录表
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_coupon_consumer")
public class CouponConsumer {
    @Id
    private Integer id;

    /**
     * 买家ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 收到的优惠券id
     */
    @Column(name = "coupon_receive_id")
    private Integer couponReceiveId;

    /**
     * 订单编号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 删除标志，默认0不删除，1删除
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