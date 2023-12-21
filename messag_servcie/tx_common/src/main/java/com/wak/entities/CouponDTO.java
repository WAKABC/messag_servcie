package com.wak.entities;

import lombok.Data;

/**
 * @author wuankang
 * @date 2023/12/6 10:38
 * @Description TODO 优惠券传递对象
 * @Version 1.0
 */
@Data
public class CouponDTO {
    /**
     * 优惠券id
     */
    private Integer couponId;
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户账号ID
     */
    private Integer userId;
}
