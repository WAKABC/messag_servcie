package com.wak.tx.msg;

import com.wak.entities.job.XxlJobProperties;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wuankang
 * @version 1.0.0
 * @date 2023/12/11
 * @description TODO
 */
@SpringBootApplication(scanBasePackages = {"com.wak.tx.msg"}, scanBasePackageClasses = XxlJobProperties.class)
@MapperScan({"com.wak.tx.msg.mapper"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.wak.tx.pay.api"})
public class TxMsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxMsgApplication.class, args);
    }

}
