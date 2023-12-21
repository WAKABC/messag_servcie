package com.wak.tx.order.api.fallback;

import com.wak.entities.order.Order;
import com.wak.tx.order.api.OrderApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author wuankang
 * @date 2023/11/21 10:05
 * @Description TODO openfeign 服务降级处理
 * @Version 1.0
 */
@Component
@Slf4j
public class OrderApiFallback implements OrderApi {
    @Override
    public List<Order> orderList() {
        log.info("rpc call orderList fail, fallback to orderList");
        return Collections.emptyList();
    }

    @Override
    public Order findOrderByOrderNo(String orderNo) {
        log.info("rpc call findOrderByOrderNo fail, fallback to findOrderByOrderNo");
        return new Order();
    }
}
