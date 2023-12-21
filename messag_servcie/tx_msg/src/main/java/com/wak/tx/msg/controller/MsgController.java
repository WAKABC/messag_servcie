package com.wak.tx.msg.controller;

import com.wak.entities.MsgDTO;
import com.wak.tx.msg.service.MsgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息表(tx_msg)表控制层
 *
 * @author xxxxx
 */
@RestController
public class MsgController {
    /**
     * 服务对象
     */
    @Resource
    private MsgService msgService;

    /**
     * 设计图第2步，接收"待发送"消息，把消息保持为“待发送”状态
     *
     * @param msgDTO 消息dto
     * @return {@code String}
     */
    @PostMapping("/msg/halfMsg")
    public void halfMsg(@RequestBody MsgDTO msgDTO) {
        msgService.addMsg(msgDTO);
    }

    /**
     * 设计图第5步，确认消息可以发送了，把消息状态改为“已发送”
     *
     * @param msgId 消息id
     */
    @GetMapping("/msg/confirmMsg/{msgId}")
    public void confirmMsg(@PathVariable("msgId") String msgId) {
        msgService.confirmMsg(msgId);
    }

    /**
     * 设计图第9步，下游系统处理完成后，将消息记录逻辑删除
     *
     * @param msgId 消息id
     */
    @GetMapping("/msg/deleteMsg/{msgId}")
    public void deleteMsg(@PathVariable("msgId") String msgId) {
        msgService.deleteMsg(msgId);
    }
}
