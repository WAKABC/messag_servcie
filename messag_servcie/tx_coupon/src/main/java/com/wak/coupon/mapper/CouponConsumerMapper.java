package com.wak.coupon.mapper;

import org.apache.ibatis.annotations.Param;

import com.wak.entities.CouponConsumer;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/11/17 16:59
 * @Description TODO
 * @Version 1.0
 */
public interface CouponConsumerMapper extends Mapper<CouponConsumer> {
    /**
     * 根据订单编号和优惠券id找到有用优惠券
     *
     * @param orderNo         订单编号
     * @param couponReceiveId 优惠券id
     * @return {@code CouponConsumer}
     */
    CouponConsumer findUsefullyCouponByOrderNoAndCouponReceiveId(@Param("orderNo") String orderNo, @Param("couponReceiveId") Integer couponReceiveId);

    /**
     * 更新tx状态和删除标志根据id
     *
     * @param status  更新tx状态
     * @param id      id
     * @param deleted 删除标志
     * @return int
     */
    int updateTxStatusAndDeletedById(@Param("status")Byte status, @Param("deleted")Byte deleted, @Param("id")Integer id);
}