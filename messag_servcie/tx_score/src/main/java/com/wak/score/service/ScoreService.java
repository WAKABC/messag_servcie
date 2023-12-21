package com.wak.score.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.wak.entities.ScoreDTO;
import com.wak.entities.ScoreDetail;
import com.wak.enums.TccEnum;
import com.wak.score.mapper.ScoreDetailMapper;
import com.wak.score.mapper.ScoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wuankang
 * @date 2023/11/17 16:53
 * @Description TODO
 * @Version 1.0
 */
@Service
@Slf4j
public class ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private ScoreDetailMapper scoreDetailMapper;

    @Transactional(rollbackFor = Exception.class)
    public String decrease(ScoreDTO scoreDTO) {
        //判断积分是否充足
        checkRemainingScore(scoreDTO);
        //幂等判断
        checkIdempotent(scoreDTO);
        //数据录入详细信息表
        saveScoreDetail(scoreDTO);
        //冻结用户积分
        freezeScore(scoreDTO);
        return "freeze score success";
    }

    /**
     * 冻结积分
     *
     * @param scoreDTO 分数dto
     */
    private void freezeScore(ScoreDTO scoreDTO) {
        scoreMapper.freezeScore(scoreDTO.getScore(), scoreDTO.getUserId());
    }

    /**
     * 保存积分详情
     *
     * @param scoreDTO 分数dto
     */
    private void saveScoreDetail(ScoreDTO scoreDTO) {
        ScoreDetail scoreDetail = BeanUtil.copyProperties(scoreDTO, ScoreDetail.class);
        scoreDetail.setTxStatus(TccEnum.TRY.getCode());
        scoreDetailMapper.save(scoreDetail);
    }

    /**
     * 幂等校验
     *
     * @param scoreDTO 分数dto
     */
    private void checkIdempotent(ScoreDTO scoreDTO) {
        ScoreDetail checkIdempotent = scoreDetailMapper.findOneByOrderNo(scoreDTO.getOrderNo());
        if (ObjUtil.isNotEmpty(checkIdempotent)) {
            throw new RuntimeException("该订单已存在");
        }
    }

    /**
     * 保存分数细节
     *
     * @param scoreDTO 积分dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveScoreDetails(ScoreDTO scoreDTO) {
        ScoreDetail scoreDetail = BeanUtil.copyProperties(scoreDTO, ScoreDetail.class);
        scoreDetail.setType((byte) 1);
        scoreDetailMapper.save(scoreDetail);
    }

    /**
     * 添加用户分数
     *
     * @param scoreDTO 分数dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUserScore(ScoreDTO scoreDTO) {
        scoreMapper.updateTotalScoreByUserId(scoreDTO.getScore(), scoreDTO.getUserId());
    }

    /**
     * 检查剩余积分
     *
     * @param scoreDTO 分数dto
     */
    private void checkRemainingScore(ScoreDTO scoreDTO) {
        int totalScore = scoreMapper.findTotalScoreByUserId(scoreDTO.getUserId());
        if (totalScore < 0 || totalScore - scoreDTO.getScore() < 0) {
            throw new RuntimeException("用户积分不足...");
        }
    }

    /**
     * 幂等检查
     * 幂等
     *
     * @param scoreDTO 积分dto
     */
    public Boolean statusCheckIdempotent(ScoreDTO scoreDTO) {
        ScoreDetail checkIdempotent = scoreDetailMapper.findOneByOrderNo(scoreDTO.getOrderNo());
        if (ObjUtil.isNotEmpty(checkIdempotent) && checkIdempotent.getType() == 1) {
            log.info("该订单已送过积分了" + scoreDTO.getOrderNo());
            return false;
        }
        return true;
    }

    /**
     * 解冻积分
     *
     * @param scoreDTO 分数dto
     */
    private void unFreezeScore(ScoreDTO scoreDTO) {
        scoreMapper.freezeScore(Math.negateExact(scoreDTO.getScore()), scoreDTO.getUserId());
    }

    /**
     * 清除冻结积分
     *
     * @param scoreDTO 分数dto
     */
    private void updateLockScore(ScoreDTO scoreDTO) {
        scoreMapper.updateDecLockScoreByUserId(scoreDTO.getScore(), scoreDTO.getUserId());
    }
}
