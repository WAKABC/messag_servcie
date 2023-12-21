package com.wak.inventory.mapper;
import org.apache.ibatis.annotations.Param;

import com.wak.entities.inventory.InventoryDetail;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/11/21 11:13
 * @Description TODO
 * @Version 1.0
 */
public interface InventoryDetailMapper extends Mapper<InventoryDetail> {

    /**
     * 幂等查询
     * @param orderNo 订单编号
     * @return {@code InventoryDetail}
     */
    Integer countByOrderNo(@Param("orderNo")String orderNo);

    /**
     * 按产品id更新tx状态
     *
     * @param status 更新tx状态
     * @param orderNo       产品id
     * @return int
     */
    int updateTxStatusByProductId(@Param("status")Byte status, @Param("orderNo")String orderNo);
}