package com.wak.score.controller;

import com.wak.entities.score.ScoreDTO;
import com.wak.score.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 积分表(tx_score)表控制层
 *
 * @author xxxxx
 */
@RestController
@Slf4j
public class ScoreController {
    /**
     * 服务对象
     */
    @Resource
    private ScoreService scoreService;

    @PostMapping("/score/decrease")
    public String decrease(@RequestBody ScoreDTO scoreDTO) {
        log.info("------进入积分controller");
        return this.scoreService.decrease(scoreDTO);
    }
}
