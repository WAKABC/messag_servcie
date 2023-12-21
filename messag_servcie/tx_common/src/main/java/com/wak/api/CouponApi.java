package com.wak.api;

import com.wak.api.fallback.CouponFallback;
import com.wak.entities.coupon.CouponDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wuankang
 * @date 2023/12/6 17:54
 * @Description TODO
 * @Version 1.0
 */
@Component
@FeignClient(name = "tx-coupon", fallback = CouponFallback.class)
public interface CouponApi {
    @PostMapping("/coupon/decrease")
    public String decrease(@RequestBody CouponDTO couponDTO);
}
