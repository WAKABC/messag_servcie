package com.wak.tx.pay.mapper;

import com.wak.entities.Pay;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/12/10 11:03
 * @Description TODO
 * @Version 1.0
 */
public interface PayMapper extends Mapper<Pay> {
    /**
     * 找到一个根据订单编号
     *
     * @param orderNo 订单编号
     * @return {@code Pay}
     */
    Pay findOneByOrderNo(@Param("orderNo")String orderNo);

    /**
     * 找到一个根据支付编号
     *
     * @param payNo 支付编号
     * @return {@code Pay}
     */
    Pay findOneByPayNo(@Param("payNo")String payNo);

    /**
     * 更新状态根据支付编号
     *
     * @param updatedStatus 更新状态
     * @param payNo         支付编号
     * @return int
     */
    int updateStatusByPayNo(@Param("updatedStatus")Byte updatedStatus,@Param("payNo")String payNo);

    /**
     * 找到状态根据订单编号
     *
     * @param orderNo 订单编号
     * @return {@code Byte}
     */
    Byte findStatusByOrderNo(@Param("orderNo") String orderNo);
}