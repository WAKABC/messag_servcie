package com.wak.tx.score.api.fallback;

import com.wak.entities.ScoreDTO;
import com.wak.tx.score.api.ScoreApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wuankang
 * @date 2023/11/21 10:05
 * @Description TODO openfeign 服务降级处理
 * @Version 1.0
 */
@Component
@Slf4j
public class ScoreApiFallback implements ScoreApi {

    @Override
    public String decrease(ScoreDTO scoreDTO) {
        return "openfeign call fail";
    }
}
