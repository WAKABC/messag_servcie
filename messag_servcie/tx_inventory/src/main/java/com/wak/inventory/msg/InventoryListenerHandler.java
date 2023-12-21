package com.wak.inventory.msg;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.wak.api.MsgApi;
import com.wak.constant.Constant;
import com.wak.entities.inventory.InventoryDTO;
import com.wak.enums.MsgEnum;
import com.wak.inventory.mapper.InventoryMapper;
import com.wak.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuankang
 * @date 2023/12/21 14:56
 * @Description TODO
 * @Version 1.0
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "consumer-inventory", topic = Constant.MQ_INVENTORY_ROUTING_KEY)
public class InventoryListenerHandler implements RocketMQListener<String> {
    @Resource
    private InventoryService inventoryService;

    @Resource
    private MsgApi msgApi;

    @Override
    public void onMessage(String s) {
        log.info("监听到主题:{},有消息, 消息内容:{}", Constant.MQ_INVENTORY_ROUTING_KEY, s);
        InventoryDTO inventoryDTO = JSONUtil.toBean(s, InventoryDTO.class);
        if (ObjUtil.isEmpty(inventoryDTO)) {
            throw new RuntimeException("库存消息对象为null");
        }
        //库存扣减
        inventoryService.decrease(inventoryDTO);
        //删除消息
        String msgId = MsgEnum.INVENTORY.getCode() + "-" + inventoryDTO.getOrderNo();
        msgApi.deleteMsg(msgId);
        log.info("消息处理完成");
    }
}
