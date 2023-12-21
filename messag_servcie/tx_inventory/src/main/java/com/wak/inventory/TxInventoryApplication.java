package com.wak.inventory;

import com.wak.api.InventoryApi;
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
@MapperScan("com.wak.inventory.mapper")
@EnableFeignClients(basePackages = {"com.wak.api"})
public class TxInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxInventoryApplication.class, args);
    }

}
