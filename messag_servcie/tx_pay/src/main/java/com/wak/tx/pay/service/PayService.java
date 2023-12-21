package com.wak.tx.pay.service;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.wak.entities.coupon.CouponDTO;
import com.wak.entities.inventory.InventoryDTO;
import com.wak.entities.msg.MsgDTO;
import com.wak.entities.pay.Pay;
import com.wak.entities.pay.PayDTO;
import com.wak.entities.score.ScoreDTO;
import com.wak.entities.order.Order;
import com.wak.entities.order.OrderDTO;
import com.wak.enums.MsgEnum;
import com.wak.tools.AssembleObjUtil;
import com.wak.tools.DatetimeUtil;
import com.wak.api.MsgApi;
import com.wak.api.OrderApi;
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
        Order order = orderApi.findOrderByOrderNo(pay.getOrderNo());
        //步骤1，发送消息给msg
        this.sendSpecifiedHalfMsg(MsgEnum.ORDER, order);
        this.sendSpecifiedHalfMsg(MsgEnum.SCORE, order);
        this.sendSpecifiedHalfMsg(MsgEnum.INVENTORY, order);
        this.sendSpecifiedHalfMsg(MsgEnum.COUPON, order);
        //步骤3，执行本地事务
        this.confirmPay(pay);
        //步骤4，发送确认消息
        this.sendSpecifiedConfirmMsg(MsgEnum.ORDER, pay.getOrderNo());
        this.sendSpecifiedConfirmMsg(MsgEnum.SCORE, pay.getOrderNo());
        this.sendSpecifiedConfirmMsg(MsgEnum.INVENTORY, pay.getOrderNo());
        this.sendSpecifiedConfirmMsg(MsgEnum.COUPON, pay.getOrderNo());
    }

    /**
     * 发送指定的半消息
     *
     * @param msgEnum 消息类型
     * @param order   订单对象
     */
    private void sendSpecifiedHalfMsg(MsgEnum msgEnum, Order order) {
        //assembly msgdto
        MsgDTO msgDTO = AssembleObjUtil.assemblyMsgDto(msgEnum, appName, order);
        //send msg
        msgApi.halfMsg(msgDTO);
    }


    /**
     * 发送指定确认消息
     *
     * @param msgEnum 消息类型
     * @param orderNo 订单编号
     */
    private void sendSpecifiedConfirmMsg(MsgEnum msgEnum, String orderNo) {
        String msgId = msgEnum.getCode() + "-" + orderNo;
        msgApi.confirmMsg(msgId);
    }

    /**
     * 消息回查
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
