package com.wak.tx.msg.xxljob;

import com.wak.constant.Constant;
import com.wak.entities.msg.Msg;
import com.wak.tx.msg.service.MsgService;
import com.wak.api.PayApi;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuankang
 * @date 2023/12/12 14:12
 * @Description TODO
 * @Version 1.0
 */
@Component
@Slf4j
public class DeliverMsgJobHandler {

    @Resource
    private MsgService msgService;

    @Resource
    private PayApi payApi;

    @XxlJob("deliverMsg")
    public void processMsg() {
        log.debug("定时轮询开始===========>轮询状态为0且已过期的消息数据");
        //定时轮询消息状态为待发送的消息
        List<Msg> msgs = msgService.selectOverdueMsgByNotSend(Constant.VERDUE_TIME);
        //遍历
        msgs.forEach(msg -> {
            log.info("匹配到相关数据，进行自动处理中....\n msg:{}", msgs);
            //解析orderId
            String orderId = msg.getMsgId().split("-")[1];
            //状态回查
            boolean result = payApi.checkMsg(orderId);
            if (ObjectUtils.isEmpty(result)) {
                log.info("状态回查失败，订单编号{}不存在", orderId);
                return;
            }
            if (result) {
                //3.消息服务对消息进行确认：如果业务执行成功，则发送MQ消息并更改消息状态为“已发送”
                msgService.confirmMsg(msg.getMsgId());
            } else {
                //3.否则删除此条消息确保数据一致性。
                msgService.deleteMsg(msg.getMsgId());
            }
        });
    }
}
