package com.wak.tx_order.msg;

import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSON;
import com.wak.constant.Constant;
import com.wak.entities.order.OrderDTO;
import com.wak.enums.MsgEnum;
import com.wak.tx.msg.api.MsgApi;
import com.wak.tx_order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuankang
 * @date 2023/12/12 20:26
 * @Description TODO msg rocket mq监听器
 * @Version 1.0
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "consumer-order", topic = Constant.MQ_ORDER_ROUTING_KEY)
public class MsgListenerHandler implements RocketMQListener<String> {

    @Resource
    private OrderService orderService;

    @Resource
    private MsgApi msgApi;

    /**
     * 第七步流程，监听消息
     *
     * @param s
     */
    @Override
    public void onMessage(String s) {
        log.info("接受到消息======>msg:{}", s);
        //转成order对象
        OrderDTO orderDTO = JSON.parseObject(s, OrderDTO.class);
        //对应设计图  第8步骤.处理本地业务tx_order表更新status字段从2=未支付改为3=支付成功
        if (ObjUtil.isEmpty(orderDTO)){
            throw new RuntimeException("orderDTO is null.");
        }
        orderService.updateStatusByOrderNo((byte)3, orderDTO.getOrderNo());
        //对应设计图 第9步，请求消息服务修改消息状态或者删除对应消息
        String msgId= MsgEnum.ORDER.getCode()+"-"+orderDTO.getOrderNo();
        msgApi.deleteMsg(msgId);
        log.info("消息处理完成");
    }
}
