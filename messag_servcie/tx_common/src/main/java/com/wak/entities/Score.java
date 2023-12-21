package com.wak.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuankang
 * @date 2023/11/17 16:53
 * @Description TODO
 * @Version 1.0
 */

/**
 * 积分表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_score")
public class Score {
    @Id
    private Integer id;

    /**
     * 用户号
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 总积分
     */
    @Column(name = "total_score")
    private Integer totalScore;

    /**
     * 冻结积分
     */
    @Column(name = "lock_score")
    private Integer lockScore;

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