package com.wak.tx_order.controller;

import cn.hutool.core.date.DateUtil;
import com.wak.entities.order.Order;
import com.wak.tools.AssembleObjUtil;
import com.wak.tx_order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单表(tx_order)表控制层
 *
 * @author xxxxx
 */
@RestController
public class OrderController {
    /**
     * 服务对象
     */
    @Resource
    private OrderService orderService;

    @GetMapping("/order/add")
    public String addOrder(@RequestParam("couponNo") Integer couponNo) {
        Order order = AssembleObjUtil.assembleOrder(couponNo);
        int i = orderService.addOrder(order);
        return "-----success add order to mysql: " + "\t" + i + "\t" + DateUtil.now();
    }


    /**
     * 查找所有的订单信息,提供给PayController类里面的getOrderList方法使用
     *
     * @return {@code List<Order>}
     */
    @GetMapping("/order/orderList")
    public List<Order> orderList(){
        return this.orderService.selectAll();
    }

    /**
     * 根据订单编号查找
     *
     * @param orderNo 订单编号
     * @return {@code Order}
     */
    @GetMapping(value = "/order/findOrderNo")
    public Order findOrderByOrderNo(@RequestParam("orderNo") String orderNo){
        return this.orderService.findOrderNo(orderNo);
    }
}
