package com.wak.tx.pay.service;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.wak.constant.Constant;
import com.wak.entities.MsgDTO;
import com.wak.entities.Pay;
import com.wak.entities.PayDTO;
import com.wak.entities.ScoreDTO;
import com.wak.entities.order.Order;
import com.wak.entities.order.OrderDTO;
import com.wak.enums.MsgEnum;
import com.wak.tools.DatetimeUtil;
import com.wak.tx.msg.api.MsgApi;
import com.wak.tx.order.api.OrderApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.wak.tx.pay.mapper.PayMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * @author wuankang
 * @date 2023/12/10 11:03
 * @Description TODO
 * @Version 1.0
 */
@Service
@Slf4j
public class PayService {

    @Resource
    private PayMapper payMapper;

    @Value("${spring.application.name}")
    private String appName;

    @Resource
    private MsgApi msgApi;

    @Resource
    private OrderApi orderApi;

    /**
     * 根据订单编号查找
     *
     * @param orderNo 订单编号
     * @return {@code Pay}
     */
    public Pay findByOrderNo(String orderNo) {
        return payMapper.findOneByOrderNo(orderNo);
    }

    /**
     * 创建支付数据
     *
     * @param payDTO 支付dto
     * @return {@code String}
     */
    @Transactional(rollbackFor = Exception.class)
    public String createPay(PayDTO payDTO) {
        Pay pay = BeanUtil.copyProperties(payDTO, Pay.class);
        //generator payNo
        Random random = new Random();
        int serialNo = random.nextInt(8999) + 1000;
        String payNo = DatetimeUtil.getCurrentDatetime() + serialNo;
        pay.setPayNo(payNo);
        pay.setStatus((byte) 0);
        payMapper.save(pay);
        return payNo;
    }


    /**
     * 确认付款处理，进行设计图流程
     *
     * @param payNo 支付编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void confirmPayHandle(String payNo) {
        Pay pay = payMapper.findOneByPayNo(payNo);
        //步骤1，发送order和score消息给msg
        this.sendOrderHalfMsg(pay);
        //获取赠送的积分数
        Order order = orderApi.findOrderByOrderNo(pay.getOrderNo());
        this.sendScoreHalfMsg(pay, order.getLargessScore());
        //步骤3，执行本地事务
        this.confirmPay(pay);
        //步骤4，通知msg修改order和score消息状态
        this.sendOrderConfirmMsg(pay.getOrderNo());
        this.sendScoreConfirmMsg(pay.getOrderNo());
    }

    /**
     * 发送积分确认消息
     *
     * @param orderNo 订单编号
     */
    private void sendScoreConfirmMsg(String orderNo) {
        String msgId = MsgEnum.SCORE.getCode() + "-" + orderNo;
        msgApi.confirmMsg(msgId);
    }

    /**
     * 发送积分半消息
     *
     * @param pay   支付
     * @param score 积分数
     */
    private void sendScoreHalfMsg(Pay pay, int score) {
        MsgDTO msgDTO = new MsgDTO();
        String msgId = MsgEnum.SCORE.getCode() + "-" + pay.getOrderNo();
        msgDTO.setMsgId(msgId);
        msgDTO.setAppName(this.appName);
        msgDTO.setRoutingKey(Constant.MQ_SCORE_ROUTING_KEY);

        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setOrderNo(pay.getOrderNo());
        scoreDTO.setUserId(pay.getUserId());
        scoreDTO.setScore(score);
        msgDTO.setJsonMsg(JSON.toJSONString(scoreDTO));
        msgApi.halfMsg(msgDTO);
    }

    /**
     * 发送订单半消息
     *
     * @param pay 支付
     */
    private void sendOrderHalfMsg(Pay pay) {
        MsgDTO msgDTO = new MsgDTO();
        String msgId = MsgEnum.ORDER.getCode() + "-" + pay.getOrderNo();
        msgDTO.setMsgId(msgId);
        msgDTO.setAppName(this.appName);
        msgDTO.setRoutingKey(Constant.MQ_ORDER_ROUTING_KEY);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(pay.getUserId());
        orderDTO.setOrderNo(pay.getOrderNo());
        msgDTO.setJsonMsg(JSON.toJSONString(orderDTO));
        //send msg
        msgApi.halfMsg(msgDTO);
    }


    /**
     * 发送订单确认消息
     *
     * @param orderNo 订单编号
     */
    private void sendOrderConfirmMsg(String orderNo) {
        String msgId = MsgEnum.ORDER.getCode() + "-" + orderNo;
        msgApi.confirmMsg(msgId);
    }

    /**
     * 检查消息
     *
     * @param orderId 消息id
     * @return boolean
     */
    public boolean checkMsg(String orderId) {
        Byte status = payMapper.findStatusByOrderNo(orderId);
        return status == 1;
    }

    /**
     * 找到根据支付编号
     *
     * @param payNo 支付编号
     * @return {@code Pay}
     */
    public Pay findByPayNo(String payNo) {
        return payMapper.findOneByPayNo(payNo);
    }

    /**
     * 确认支付
     *
     * @param pay 支付
     */
    public void confirmPay(Pay pay) {
        payMapper.updateStatusByPayNo((byte) 1, pay.getPayNo());
    }
}
