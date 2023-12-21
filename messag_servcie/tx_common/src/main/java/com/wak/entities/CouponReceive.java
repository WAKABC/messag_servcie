package com.wak.entities;

import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/11/17 16:59
 * @Description TODO 优惠券领取记录表
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponReceive {
    private Integer id;

    /**
     * 买家ID
     */
    private Integer userId;

    /**
     * 优惠券id
     */
    private Integer couponId;

    /**
     * 券额
     */
    private Integer couponMoney;

    /**
     * 状态，1为已领取未使用，2为已使用，3为已过期
     */
    private Byte status;

    /**
     * 删除标志，默认0不删除，1删除
     */
    private Byte deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}