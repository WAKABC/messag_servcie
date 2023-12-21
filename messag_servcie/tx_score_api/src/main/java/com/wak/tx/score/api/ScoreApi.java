package com.wak.tx.score.api;

import com.wak.entities.ScoreDTO;
import com.wak.tx.score.api.fallback.ScoreApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wuankang
 * @date 2023/11/21 9:59
 * @Description TODO
 * @Version 1.0
 */
@Component
@FeignClient(value = "tx-score", fallback =  ScoreApiFallback.class)
public interface ScoreApi {
    @PostMapping("/score/decrease")
    public String decrease(@RequestBody ScoreDTO scoreDTO);
}
