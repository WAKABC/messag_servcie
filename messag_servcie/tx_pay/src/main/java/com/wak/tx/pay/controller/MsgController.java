package com.wak.tx.pay.controller;

import com.wak.tx.pay.service.PayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuankang
 * @date 2023/12/12 14:25
 * @Description TODO 消息回查controller
 * @Version 1.0
 */
@RestController
public class MsgController {
    @Resource
    private PayService payService;

    /**
     * 消息回查
     *
     * @param orderId 消息id
     * @return boolean
     */
    @GetMapping("/pay/checkMsg")
    public boolean checkMsg(@RequestParam("orderId") String orderId){
        return payService.checkMsg(orderId);
    }
}
