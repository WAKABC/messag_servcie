package com.wak.entities;

import lombok.Data;

/**
 * @author wuankang
 * @date 2023/11/29 10:21
 * @Description TODO openfeign数据传递对象
 * @Version 1.0
 */
@Data
public class InventoryDTO {
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户账号ID
     */
    private Integer userId;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 数量
     */
    private Integer count;
}
