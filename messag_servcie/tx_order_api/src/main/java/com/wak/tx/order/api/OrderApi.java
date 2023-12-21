package com.wak.tx.order.api;

import com.wak.entities.order.Order;
import com.wak.tx.order.api.fallback.OrderApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wuankang
 * @date 2023/11/21 9:59
 * @Description TODO
 * @Version 1.0
 */
@Component
@FeignClient(value = "tx-order", fallback =  OrderApiFallback.class)
public interface OrderApi {
    @GetMapping(value = "/order/orderList")
    public List<Order> orderList();

    @GetMapping(value = "/order/findOrderNo")
    public Order findOrderByOrderNo(@RequestParam("orderNo") String orderNo);
}
