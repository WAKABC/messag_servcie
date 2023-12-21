package com.wak.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.wak.score.mapper")
@EnableFeignClients(basePackages = {"com.wak.api"})
public class TxScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxScoreApplication.class, args);
    }

}
