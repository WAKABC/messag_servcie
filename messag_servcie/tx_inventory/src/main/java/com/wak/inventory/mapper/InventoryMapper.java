package com.wak.inventory.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.wak.entities.inventory.Inventory;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/11/21 11:11
 * @Description TODO
 * @Version 1.0
 */
public interface InventoryMapper extends Mapper<Inventory> {

    /**
     * 按产品编号获取总库存
     *
     * @param productid 产品id
     * @return {@code List<Integer>}
     */
    List<Integer> getTotalInventoryByProductid(@Param("productid")Integer productid);

    /**
     * 更新库存数根据产品id
     * 按产品id更新库存数量
     *
     * @param count     产品数量
     * @param productid productid
     * @return int
     */
    int updateInventoryCountByProductId(@Param("count")Integer count, @Param("productid")Integer productid);

    /**
     * 付款成功，解锁库存数量
     *
     * @param count 库存数量
     * @param productid 产品编号
     * @return int
     */
    int updateDecLockInventoryByProductid(@Param("count")Integer count, @Param("productid")Integer productid);

}