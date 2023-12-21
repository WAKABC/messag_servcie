package com.wak.entities.score;

import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/11/17 16:55
 * @Description TODO
 * @Version 1.0
 */

/**
 * @author wuankang
 * @version 1.0.0
 * @date 2023/12/05
 * @description TODO 积分详细表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_score_detail")
public class ScoreDetail {
    @Id
    private Integer id;

    /**
     * 订单编号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 订单状态
     */
    @Column(name = "tx_status")
    private Byte txStatus;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 积分
     */
    @Column(name = "score")
    private Integer score;

    /**
     * 积分类型 0=消耗，1=新增
     */
    @Column(name = "type")
    private Byte type;

    /**
     * 删除标志
     */
    @Column(name = "deleted")
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