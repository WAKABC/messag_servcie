package com.wak.coupon.msg;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.wak.api.MsgApi;
import com.wak.constant.Constant;
import com.wak.coupon.service.CouponService;
import com.wak.entities.coupon.CouponDTO;
import com.wak.enums.MsgEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuankang
 * @date 2023/12/21 15:32
 * @Description TODO
 * @Version 1.0
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "consumer-coupon", topic = Constant.MQ_COUPON_ROUTING_KEY)
public class CouponListenerHandler implements RocketMQListener<String> {
    @Resource
    private CouponService couponService;

    @Resource
    private MsgApi msgApi;

    @Override
    public void onMessage(String s) {
        log.info("监听到主题:{},有消息, 消息内容:{}", Constant.MQ_COUPON_ROUTING_KEY, s);
        CouponDTO couponDTO = JSONUtil.toBean(s, CouponDTO.class);
        if (ObjUtil.isEmpty(couponDTO)) {
            throw new RuntimeException("优惠券消息对象为null");
        }
        //抵扣优惠券
        couponService.decrease(couponDTO);
        //删除消息
        String msgId = MsgEnum.INVENTORY.getCode() + "-" + couponDTO.getOrderNo();
        msgApi.deleteMsg(msgId);
        log.info("消息处理完成");
    }
}
