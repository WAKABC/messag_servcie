package com.wak.api;

import com.wak.api.fallback.ScoreApiFallback;
import com.wak.entities.score.ScoreDTO;
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
