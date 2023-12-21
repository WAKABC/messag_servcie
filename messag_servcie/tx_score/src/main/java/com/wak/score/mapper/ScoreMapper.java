package com.wak.score.mapper;

import org.apache.ibatis.annotations.Param;

import com.wak.entities.score.Score;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/11/17 16:53
 * @Description TODO
 * @Version 1.0
 */
public interface ScoreMapper extends Mapper<Score> {
    /**
     * 根据用户id找到用户积分
     *
     * @param userId 用户id
     * @return {@code List<String>}
     */
    Integer findTotalScoreByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户id扣减冻结积分
     *
     * @param score  分数
     * @param userId 用户id
     * @return int
     */
    int updateDecLockScoreByUserId(@Param("score")Integer score, @Param("userId")Integer userId);

    /**
     * 冻结积分
     *
     * @param score  积分
     * @param userId 用户id
     * @return int
     */
    int freezeScore(@Param("score") Integer score, @Param("userId") Integer userId);

    /**
     * 更新总计。分数根据用户id
     *
     * @param updatedTotalScore 更新总分
     * @param userId            用户id
     * @return int
     */
    int updateTotalScoreByUserId(@Param("updatedTotalScore")Integer updatedTotalScore,@Param("userId")Integer userId);
}