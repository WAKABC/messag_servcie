package com.wak.coupon.controller;

import com.wak.entities.Coupon;
import com.wak.coupon.service.CouponService;
import com.wak.entities.CouponDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 优惠券(tx_coupon)表控制层
 *
 * @author xxxxx
 */
@RestController
@Slf4j
public class CouponController {
    /**
     * 服务对象
     */
    @Resource
    private CouponService couponService;

    @PostMapping("/coupon/decrease")
    public String decrease(@RequestBody CouponDTO couponDTO)
    {
        log.info("------进入优惠卷controller");
        this.couponService.decrease(couponDTO);
        return "优惠卷 OK";
    }

}
