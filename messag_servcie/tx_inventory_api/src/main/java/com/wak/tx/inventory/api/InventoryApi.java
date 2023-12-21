package com.wak.tx.inventory.api;

import com.wak.entities.InventoryDTO;
import com.wak.tx.inventory.api.fallback.InventoryApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wuankang
 * @date 2023/11/21 11:19
 * @Description TODO 
 * @Version 1.0
 */
@Component
@FeignClient(value = "tx-inventory", fallback = InventoryApiFallback.class)
public interface InventoryApi {
    @PostMapping("/inventory/decrease")
    public String decrease(@RequestBody InventoryDTO inventoryDTO);
}
