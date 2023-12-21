package com.wak.tx_order.service;

import cn.hutool.core.util.ObjUtil;
import com.wak.entities.CouponDTO;
import com.wak.entities.InventoryDTO;
import com.wak.entities.ScoreDTO;
import com.wak.entities.order.Order;
import com.wak.tx.coupon.api.CouponApi;
import com.wak.tx.inventory.api.InventoryApi;
import com.wak.tx.score.api.ScoreApi;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.wak.tx_order.mapper.OrderMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wuankang
 * @date 2023/11/17 10:18
 * @Description TODO
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private InventoryApi inventoryApi;

    @Resource
    private ScoreApi scoreApi;

    @Resource
    private CouponApi couponApi;

    /**
     * 添加订单 --->减库存 --->抵扣积分 --->扣优惠券：分布式事务框架hmily
     *
     * @param order 订单
     * @return int
     */
    @Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    @Transactional(rollbackFor = Exception.class)
    public int addOrder(Order order) {
        //创建订单
        int count = orderMapper.insert(order);
        if (count <= 0) return 0;
        //扣减库存
        InventoryDTO inventoryDTO=new InventoryDTO();

        inventoryDTO.setUserId(order.getUserId());
        inventoryDTO.setProductId(order.getProductId());
        inventoryDTO.setCount(order.getProductCount());
        inventoryDTO.setOrderNo(order.getOrderNo());
        inventoryApi.decrease(inventoryDTO);
        //抵扣积分
        ScoreDTO scoreDTO=new ScoreDTO();
        scoreDTO.setUserId(order.getUserId());
        scoreDTO.setOrderNo(order.getOrderNo());
        scoreDTO.setScore(order.getDecreaseScore());
        scoreApi.decrease(scoreDTO);
        //扣除优惠券
        CouponDTO couponDTO=new CouponDTO();
        couponDTO.setUserId(order.getUserId());
        couponDTO.setOrderNo(order.getOrderNo());
        couponDTO.setCouponId(order.getCouponReceiveId());
        couponApi.decrease(couponDTO);
        return 1;
    }

    /**
     * hmily confirmMethod
     *
     * @param order 订单
     * @return int
     */
    public int confirm(Order order) {
        log.info("------confirm-----");
        //确认订单是否存在
        Order orderEntity = this.orderMapper.findOneByOrderNo(order.getOrderNo());
        if (ObjUtil.isNotEmpty(orderEntity)) {
            this.orderMapper.updateStatusByOrderNo((byte) 2, order.getOrderNo());
            return 1;
        }
        log.info("------confirm, 订单不存在, orderNo:{}------", order.getOrderNo());
        return -1;
    }

    /**
     * hmily cancelMethod
     *
     * @param order 订单
     * @return int
     */
    public int cancel(Order order) {
        log.info("--------cancel------");
        //查询订单
        Order orderEntity = this.orderMapper.findOneByOrderNo(order.getOrderNo());
        if (ObjUtil.isNotEmpty(orderEntity)) {
            this.orderMapper.updateStatusByOrderNo((byte) 1, order.getOrderNo());
            return 1;
        }
        log.info("------cancel, 订单不存在, orderNo:{}------", order.getOrderNo());
        return -1;
    }

    /**
     * 查找所有的订单信息,提供给PayController类里面的getOrderList方法使用
     *
     * @return {@code List<Order>}
     */
    public List<Order> selectAll() {
        return orderMapper.findAll();
    }

    /**
     * 根据订单号查找订单
     *
     * @param orderNo 订单编号
     * @return {@code Order}
     */
    public Order findOrderNo(String orderNo) {
        return this.orderMapper.findOneByOrderNo(orderNo);
    }

    /**
     * 更新状态根据订单编号
     *
     * @param status  状态
     * @param orderNo 订单编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusByOrderNo(Byte status, String orderNo){
        orderMapper.updateStatusByOrderNo(status, orderNo);
    }
}
