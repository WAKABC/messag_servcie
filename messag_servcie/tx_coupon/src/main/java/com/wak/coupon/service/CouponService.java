package com.wak.coupon.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.wak.coupon.mapper.CouponConsumerMapper;
import com.wak.coupon.mapper.CouponReceiveMapper;
import com.wak.entities.CouponConsumer;
import com.wak.entities.CouponDTO;
import com.wak.entities.CouponReceive;
import com.wak.enums.TccEnum;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    public String decrease(CouponDTO couponDTO) {
        //判断优惠券充足
        checkUseful(couponDTO);
        //幂等校验
        checkIdempotent(couponDTO);
        //保存到优惠券消费表
        saveCouponConsumer(couponDTO);
        return "decrease success";
    }

    /**
     * 确认
     *
     * @param couponDTO 优惠券dto
     * @return {@code String}
     */
    @Transactional(rollbackFor = Exception.class)
    public String confirm(CouponDTO couponDTO) {
        //幂等判断
        CouponConsumer checkIdempotent = checkStatusIdempotent(couponDTO, TccEnum.CONFIRM);
        //修改事务状态
        updateTxStatusAndDeletedById(TccEnum.CONFIRM, (byte) 0, checkIdempotent);
        //更新使用状态
        updateCouponStatus(checkIdempotent);
        return "confirm success";
    }

    /**
     * 取消
     *
     * @param couponDTO 优惠券dto
     * @return {@code String}
     */
    @Transactional(rollbackFor = Exception.class)
    public String cancel(CouponDTO couponDTO) {
        //幂等判断
        CouponConsumer checkIdempotent = checkStatusIdempotent(couponDTO, TccEnum.CANCEL);
        //删除消费表中的优惠券
        updateTxStatusAndDeletedById(TccEnum.CANCEL, (byte) 1, checkIdempotent);
        return "cancel success";
    }

    /**
     * 保存使用的优惠券
     *
     * @param couponDTO 优惠券dto
     */
    private void saveCouponConsumer(CouponDTO couponDTO) {
        CouponConsumer couponConsumer = BeanUtil.copyProperties(couponDTO, CouponConsumer.class);
        couponConsumer.setTxStatus(TccEnum.TRY.getCode());
        couponConsumerMapper.save(couponConsumer);
    }

    /**
     * 幂等校验
     *
     * @param couponDTO 优惠券dto
     */
    private void checkIdempotent(CouponDTO couponDTO) {
        CouponConsumer checkIdempotent = couponConsumerMapper.findUsefullyCouponByOrderNoAndCouponReceiveId(couponDTO.getOrderNo(), couponDTO.getCouponId());
        if (ObjUtil.isNotEmpty(checkIdempotent)) {
            throw new RuntimeException("该订单已存在");
        }
    }

    /**
     * 检查优惠券是否可用
     *
     * @param couponDTO 优惠券dto
     */
    private void checkUseful(CouponDTO couponDTO) {
        CouponReceive coupon = couponReceiveMapper.findOneByCouponId(couponDTO.getCouponId());
        if (ObjUtil.isEmpty(coupon) || coupon.getStatus() != 1) {
            throw new RuntimeException("无可用优惠券");
        }
    }

    /**
     * 更新优惠券状态
     *
     * @param checkIdempotent 幂等校验
     */
    private void updateCouponStatus(CouponConsumer checkIdempotent) {
        couponReceiveMapper.updateStatusById((byte) 2, checkIdempotent.getId());
    }

    /**
     * 更新状态和是否删除二合一
     *
     * @param entity          实体
     * @param checkIdempotent 幂等校验
     */
    private void updateTxStatusAndDeletedById(TccEnum entity, byte deleted, CouponConsumer checkIdempotent) {
        couponConsumerMapper.updateTxStatusAndDeletedById(entity.getCode(), deleted, checkIdempotent.getId());
    }

    /**
     * 检查订单状态是否幂等
     *
     * @param couponDTO 优惠券dto
     * @param entity    事务状态枚举对象
     * @return {@code CouponConsumer}
     */
    private CouponConsumer checkStatusIdempotent(CouponDTO couponDTO, TccEnum entity) {
        CouponConsumer checkIdempotent = couponConsumerMapper.findUsefullyCouponByOrderNoAndCouponReceiveId(couponDTO.getOrderNo(), couponDTO.getCouponId());
        if (ObjUtil.isNotEmpty(checkIdempotent) && checkIdempotent.getTxStatus() == entity.getCode()) {
            throw new RuntimeException("该订单已经" + entity.getDesc());
        }
        return checkIdempotent;
    }


}
