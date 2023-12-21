package com.wak.api;

import com.wak.api.fallback.InventoryApiFallback;
import com.wak.entities.inventory.InventoryDTO;
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
