package com.wak.tools;

import cn.hutool.core.util.IdUtil;
import com.wak.entities.order.Order;

import java.math.BigDecimal;

/**
 * @author wuankang
 * @date 2023/11/19 14:09
 * @Description TODO
 * @Version 1.0
 */
public class AssembleObjUtil {

    public static Order assembleOrder(Integer couponNo){
        Order order = new Order();
        order.setUserId(1);
        order.setProductId(1);
        order.setPaymentMoney(new BigDecimal(100));
        order.setOrderNo(IdUtil.simpleUUID());
        order.setProductCount(1);
        order.setDecreaseScore(2);
        order.setCouponReceiveId(couponNo);
        return order;
    }
}
