package com.wak.tx.msg.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.wak.entities.msg.Msg;
import com.wak.entities.msg.MsgDTO;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.wak.tx.msg.mapper.MsgMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wuankang
 * @date 2023/12/11 16:19
 * @Description TODO
 * @Version 1.0
 */
@Service
public class MsgService {

    @Resource
    private MsgMapper msgMapper;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 创建消息记录
     *
     * @param msgDTO 消息dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void addMsg(MsgDTO msgDTO) {
        Msg msg = BeanUtil.copyProperties(msgDTO, Msg.class);
        //set status
        msg.setStatus((byte) 0);
        //save
        msgMapper.save(msg);
    }

    /**
     * 修改状态为已发送
     *
     * @param msgId 消息id
     */
    @Transactional(rollbackFor = Exception.class)
    public void confirmMsg(String msgId) {
        //幂等
        Msg msg = msgMapper.findOneByMsgId(msgId);
        if (ObjUtil.isNotEmpty(msg) && msg.getStatus().byteValue() == 1) {
            throw new RuntimeException("消息已发送");
        }
        int count = msgMapper.updateStatusByMsgId((byte) 1, msgId);
        if (count > 0) {
            //发送mq消息
            rocketMQTemplate.convertAndSend(msg.getRoutingKey(), msg.getJsonMsg());
        }
    }


    /**
     * 删除消息，delete=1
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteMsg(String msgId) {
        msgMapper.updateDeletedByMsgId((byte) 1, msgId);
    }

    /**
     * 下面是给定时任务xxl-job配合使用的
     *
     * @param t
     * @return
     */
    public List<Msg> selectOverdueMsgByNotSend(int t) {
        return this.msgMapper.selectOverdueMsg((byte) 0, t);
    }

    public List<Msg> selectOverdueMsgBySend(int t) {
        return this.msgMapper.selectOverdueMsg((byte) 1, t);
    }
}
