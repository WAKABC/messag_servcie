package com.wak.tx_order.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.wak.entities.order.Order;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/11/17 10:18
 * @Description TODO
 * @Version 1.0
 */
public interface OrderMapper extends Mapper<Order> {
    List<Order> findAll();
    Order findOneByOrderNo(@Param("orderNo")String orderNo);
    int updateStatusByOrderNo(@Param("status")Byte status, @Param("orderNo")String orderNo);
}