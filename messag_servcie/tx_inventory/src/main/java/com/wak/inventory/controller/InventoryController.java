package com.wak.inventory.controller;

import com.wak.entities.inventory.InventoryDTO;
import com.wak.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 库存表(tx_inventory)表控制层
 *
 * @author xxxxx
 */
@RestController
@Slf4j
public class InventoryController {
    /**
     * 服务对象
     */
    @Resource
    private InventoryService inventoryService;

    @PostMapping("/inventory/decrease")
    public String decrease(@RequestBody InventoryDTO inventoryDTO){
        log.info("calling inventory decrease method:{}",inventoryDTO);
        inventoryService.decrease(inventoryDTO);
        return "success";
    }
}
