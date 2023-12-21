package com.wak.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wuankang
 * @version 1.0.0
 * @date 2023/12/12
 * @description TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.wak.coupon.mapper")
@EnableFeignClients(basePackages = {"com.wak.tx.coupon.api"})
public class TxCouponApplication {
	public static void main(String[] args) {
		SpringApplication.run(TxCouponApplication.class, args);
	}
}
