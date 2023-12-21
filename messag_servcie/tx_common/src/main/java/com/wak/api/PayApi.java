package com.wak.api;

import com.wak.api.fallback.PayFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuankang
 * @date 2023/12/12 11:29
 * @Description TODO
 * @Version 1.0
 */
@FeignClient(value = "tx-pay", fallback = PayFallback.class)
public interface PayApi {
    /**
     * 检查消息
     *
     * @param orderId 消息id
     * @return boolean
     */
    @GetMapping("/pay/checkMsg")
    boolean checkMsg(@RequestParam("orderId") String orderId);
}
