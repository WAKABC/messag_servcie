package com.wak.api.fallback;

import com.wak.entities.inventory.InventoryDTO;
import com.wak.api.InventoryApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wuankang
 * @date 2023/11/21 11:20
 * @Description TODO 服务降级
 * @Version 1.0
 */
@Slf4j
@Component
public class InventoryApiFallback implements InventoryApi {
    @Override
    public String decrease(InventoryDTO inventoryDTO) {
        log.info("rpc call decrease fail, fallback to decrease");
        return "error rpc call decrease fail";
    }
}
