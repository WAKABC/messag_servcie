package com.wak.tx.msg.api;

import com.wak.entities.MsgDTO;
import com.wak.tx.msg.api.fallback.MsgFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wuankang
 * @date 2023/12/12 11:29
 * @Description TODO
 * @Version 1.0
 */
@FeignClient(value = "tx-msg", fallback = MsgFallback.class)
public interface MsgApi {
    /**
     * 一半消息
     * 设计图第2步，接收"待发送"消息，把消息保持为“待发送”状态
     *
     * @param msgDTO 消息dto
     */
    @PostMapping("/msg/halfMsg")
    public void halfMsg(@RequestBody MsgDTO msgDTO);

    /**
     * 确认消息
     * 设计图第5步，确认消息可以发送了，把消息状态改为“已发送”
     *
     * @param msgId 消息id
     */
    @GetMapping("/msg/confirmMsg/{msgId}")
    public void confirmMsg(@PathVariable("msgId") String msgId);

    /**
     * 删除消息
     * 设计图第9步，把消息直接删除，当然，也可以不删除
     *
     * @param msgId 消息id
     **/
    @GetMapping("/msg/deleteMsg/{msgId}")
    public void deleteMsg(@PathVariable("msgId") String msgId);
}
