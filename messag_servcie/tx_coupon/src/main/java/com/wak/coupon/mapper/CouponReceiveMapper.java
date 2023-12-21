package com.wak.coupon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wak.entities.coupon.CouponReceive;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/11/17 16:59
 * @Description TODO
 * @Version 1.0
 */
public interface CouponReceiveMapper extends Mapper<CouponReceive> {
    /**
     * 根据优惠券id查询优惠券
     *
     * @param couponId 优惠券id
     * @return {@code CouponReceive}
     */
    CouponReceive findOneByCouponId(@Param("couponId") Integer couponId);

    /**
     * 更新状态和删除根据优惠券id
     *
     * @param status          更新状态
     * @param couponReceiveId 优惠券id
     * @return int
     */
    int updateStatusAndDeletedByCouponId(@Param("status") Byte status, @Param("couponReceiveId") Integer couponReceiveId);

    /**
     * 找到可用的优惠券根据优惠券id
     *
     * @param couponId   优惠券id
     * @param expiryDate 截止日期
     * @return {@code CouponReceive}
     */
    CouponReceive findUsefulCouponByCouponId(@Param("couponId") Integer couponId, @Param("expiryDate") Integer expiryDate);


}