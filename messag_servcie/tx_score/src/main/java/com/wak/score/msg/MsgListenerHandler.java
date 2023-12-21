package com.wak.score.msg;

import com.alibaba.fastjson.JSON;
import com.wak.constant.Constant;
import com.wak.entities.ScoreDTO;
import com.wak.enums.MsgEnum;
import com.wak.score.service.ScoreService;
import com.wak.tx.msg.api.MsgApi;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuankang
 * @date 2023/12/16 21:30
 * @Description TODO
 * @Version 1.0
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer-score", topic = Constant.MQ_SCORE_ROUTING_KEY)
public class MsgListenerHandler implements RocketMQListener<String> {
    @Resource
    private ScoreService scoreService;

    @Resource
    private MsgApi msgApi;

    @Override
    public void onMessage(String s) {
        ScoreDTO scoreDTO = JSON.parseObject(s, ScoreDTO.class);
        //幂等
        Boolean flag = scoreService.statusCheckIdempotent(scoreDTO);
        if (!flag) return;
        //增加用户积分详细数据
        scoreService.saveScoreDetails(scoreDTO);
        //增加用户总积分
        scoreService.addUserScore(scoreDTO);
        //删除消息数据
        String msgId = MsgEnum.SCORE.getCode() + "-" + scoreDTO.getOrderNo();
        msgApi.deleteMsg(msgId);
    }
}
