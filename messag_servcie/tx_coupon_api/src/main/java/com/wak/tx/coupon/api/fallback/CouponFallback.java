package com.wak.tx.coupon.api.fallback;

import com.wak.entities.CouponDTO;
import com.wak.tx.coupon.api.CouponApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wuankang
 * @date 2023/12/6 17:57
 * @Description TODO
 * @Version 1.0
 */
@Slf4j
@Component
public class CouponFallback implements CouponApi {
    @Override
    public String decrease(CouponDTO couponDTO) {
        return "error rpc call tx-coupon decrease fail";
    }
}
