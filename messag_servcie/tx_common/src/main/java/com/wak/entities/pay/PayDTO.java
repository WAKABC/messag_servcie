package com.wak.entities.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuankang
 * @date 2023/12/10 17:08
 * @Description TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDTO {
    private Integer id;

    /**
     * 交易单号
     */
    private String payNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户账号ID
     */
    private Integer userId;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 支付类型 0:余额 1:微信 2:支付宝 3:xxx
     */
    private Byte payState;

    /**
     * 支付来源 wx app web wap
     */
    private String source;

    /**
     * 支付状态 -1：取消 0 未完成 1已完成 -2:异常
     */
    private Byte status;

    /**
     * 删除标志，默认0不删除，1删除
     */
    private Byte deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
