package com.wak.score.mapper;
import org.apache.ibatis.annotations.Param;

import com.wak.entities.score.ScoreDetail;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/11/17 16:55
 * @Description TODO
 * @Version 1.0
 */
public interface ScoreDetailMapper extends Mapper<ScoreDetail> {

    /**
     * 根据订单编号查找唯一数据
     *
     * @param orderNo 订单编号
     * @return {@code ScoreDetail}
     */
    ScoreDetail findOneByOrderNo(@Param("orderNo")String orderNo);

    /**
     * 根据订单编号更新tx状态
     *
     * @param updatedTxStatus 更新tx状态
     * @param orderNo         订单编号
     * @return int
     */
    int updateTxStatusByOrderNo(@Param("updatedTxStatus")Byte updatedTxStatus,@Param("orderNo")String orderNo);
}