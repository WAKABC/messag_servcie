package com.wak.entities;

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
public class CouponConsumer {
    private Integer id;

    /**
     * 买家ID
     */
    private Integer userId;

    /**
     * 收到的优惠券id
     */
    private Integer couponReceiveId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 状态
     */
    private Byte txStatus;

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