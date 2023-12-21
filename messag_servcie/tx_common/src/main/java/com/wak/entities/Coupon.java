package com.wak.entities;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/11/17 16:58
 * @Description TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 所属类型,1=无门槛，2=满减
     */
    private byte type;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券金额，用整数，固定值目前。
     */
    private Integer money;

    /**
     * 状态，0表示未开始，1表示进行中，2表示结束
     */
    private byte status;

    /**
     * 删除标志，默认0不删除，1删除
     */
    private byte deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}