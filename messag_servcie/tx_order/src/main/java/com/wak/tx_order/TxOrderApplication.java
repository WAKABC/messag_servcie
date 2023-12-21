package com.wak.tx_order;

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
@MapperScan(basePackages = "com.wak.tx_order.mapper")
@EnableFeignClients(basePackages = {"com.wak.tx.inventory.api", "com.wak.tx.order.api", "com.wak.tx.score.api", "com.wak.tx.coupon.api", "com.wak.tx.msg.api"})
public class TxOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxOrderApplication.class, args);
    }

}
