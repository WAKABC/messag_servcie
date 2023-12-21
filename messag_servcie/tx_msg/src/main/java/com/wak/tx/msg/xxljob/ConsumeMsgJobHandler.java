package com.wak.tx.msg.xxljob;

import com.wak.constant.Constant;
import com.wak.entities.msg.Msg;
import com.wak.tx.msg.service.MsgService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuankang
 * @date 2023/12/12 15:08
 * @Description TODO
 * @Version 1.0
 */
@Component
public class ConsumeMsgJobHandler {
    @Resource
    private MsgService msgService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @XxlJob("consumeMsg")
    public void processMsg() {
        //获取状态为已发送的消息
        List<Msg> msgs = msgService.selectOverdueMsgBySend(Constant.VERDUE_TIME);
        msgs.forEach(msg -> {
            //重新投递
            rocketMQTemplate.convertAndSend(msg.getRoutingKey(), msg.getJsonMsg());
        });
    }
}

