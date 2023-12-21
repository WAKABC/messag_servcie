package com.wak.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuankang
 * @date 2023/12/10 10:43
 * @Description TODO
 * @Version 1.0
 */
@Table(name = "tx_pay")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pay {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 交易单号
     */
    @Column(name = "pay_no")
    private String payNo;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 用户账号ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 支付类型 0:余额 1:微信 2:支付宝 3:xxx
     */
    @Column(name = "pay_state")
    private Byte payState;


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
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}
