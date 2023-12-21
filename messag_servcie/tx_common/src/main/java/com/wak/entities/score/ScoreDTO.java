package com.wak.entities.score;

import lombok.Data;

/**
 * @author wuankang
 * @date 2023/12/5 9:47
 * @Description TODO 积分dto
 * @Version 1.0
 */
@Data
public class ScoreDTO {
    private Integer id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户账号ID
     */
    private Integer userId;

    /**
     * 积分
     */
    private Integer largessScore;
}
