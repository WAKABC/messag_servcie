package com.wak.tx.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wuankang
 * @version 1.0.0
 * @date 2023/12/10
 * @description TODO
 */
@SpringBootApplication
@MapperScan({"com.wak.tx.pay.mapper"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.wak.tx.msg.api", "com.wak.tx.order.api"})
public class TxPayApplication {
	public static void main(String[] args) {
		SpringApplication.run(TxPayApplication.class, args);
	}
}
