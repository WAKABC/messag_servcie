package com.wak.coupon.mapper;
import org.apache.ibatis.annotations.Param;

import com.wak.entities.CouponReceive;
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
    CouponReceive findOneByCouponId(@Param("couponId")Integer couponId);

    /**
     * 更新状态根据id
     *
     * @param updatedStatus 更新状态
     * @param id            id
     * @return int
     */
    int updateStatusById(@Param("updatedStatus")Byte updatedStatus,@Param("id")Integer id);

}