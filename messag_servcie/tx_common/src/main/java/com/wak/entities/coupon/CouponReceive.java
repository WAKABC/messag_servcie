package com.wak.entities.coupon;

import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

/**
 * @author wuankang
 * @date 2023/11/17 16:59
 * @Description TODO 优惠券领取记录表
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_coupon_receive")
public class CouponReceive {
    @Id
    private Integer id;

    /**
     * 买家ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 优惠券id
     */
    @Column(name = "coupon_id")
    private Integer couponId;

    /**
     * 券额
     */
    @Column(name = "coupon_money")
    private Integer couponMoney;

    /**
     * 有效期（小时）
     */
    @Column(name = "expiry_date")
    private Integer expiryDate;

    /**
     * 状态，1为已领取未使用，2为已使用，3为已过期
     */
    @Column(name = "status")
    private Byte status;

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