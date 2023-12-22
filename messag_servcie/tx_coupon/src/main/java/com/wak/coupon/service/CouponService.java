package com.wak.coupon.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.wak.coupon.mapper.CouponConsumerMapper;
import com.wak.coupon.mapper.CouponReceiveMapper;
import com.wak.entities.coupon.CouponConsumer;
import com.wak.entities.coupon.CouponDTO;
import com.wak.entities.coupon.CouponReceive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Detainted;
import javax.annotation.Resource;

/**
 * @author wuankang
 * @date 2023/11/17 16:58
 * @Description TODO
 * @Version 1.0
 */
@Service
@Slf4j
public class CouponService {

    @Resource
    private CouponConsumerMapper couponConsumerMapper;

    @Resource
    private CouponReceiveMapper couponReceiveMapper;

    /**
     * 使用优惠券
     *
     * @param couponDTO 优惠券dto
     * @return {@code String}
     */
    @Transactional(rollbackFor = Exception.class)
    public void decrease(CouponDTO couponDTO) {
        //幂等校验
        checkIdempotent(couponDTO);
        //保存到优惠券消费表
        saveCouponConsumer(couponDTO);
        //删除已用的优惠券
        deletedCoupon(couponDTO.getCouponReceiveId());
    }

    /**
     * 保存使用的优惠券
     *
     * @param couponDTO 优惠券dto
     */
    private void saveCouponConsumer(CouponDTO couponDTO) {
        CouponConsumer couponConsumer = BeanUtil.copyProperties(couponDTO, CouponConsumer.class);
        couponConsumerMapper.insertSelective(couponConsumer);
    }

    /**
     * 幂等校验
     *
     * @param couponDTO 优惠券dto
     */
    private void checkIdempotent(CouponDTO couponDTO) {
        CouponConsumer checkIdempotent = couponConsumerMapper.findUsefullyCouponByOrderNoAndCouponReceiveId(couponDTO.getOrderNo(), couponDTO.getCouponReceiveId());
        if (ObjUtil.isNotEmpty(checkIdempotent)) {
            throw new RuntimeException("该订单已存在");
        }
    }

    /**
     * 检查优惠券是否可用
     * 独立消息服务架构不能处理业务异常，此方法弃用
     * @param couponDTO 优惠券dto
     */
    @Deprecated
    private void checkUseful(CouponDTO couponDTO) {
        CouponReceive coupon = couponReceiveMapper.findOneByCouponId(couponDTO.getCouponReceiveId());
        if (ObjUtil.isEmpty(coupon)) {
            throw new RuntimeException("无该id的优惠券");
        }
        CouponReceive usefulCoupon = couponReceiveMapper.findUsefulCouponByCouponId(coupon.getCouponId(), coupon.getExpiryDate());
        if (ObjUtil.isEmpty(usefulCoupon)) {
            throw new RuntimeException("该优惠券已使用或已过期，无法使用");
        }
    }

    /**
     * 更新优惠券状态
     *
     * @param couponReceiveId 优惠券id
     */
    private void deletedCoupon(int couponReceiveId) {
        couponReceiveMapper.updateStatusAndDeletedByCouponId((byte) 2, couponReceiveId);
    }
}
