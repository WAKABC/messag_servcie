package com.wak.inventory.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.wak.entities.inventory.Inventory;
import com.wak.entities.inventory.InventoryDTO;
import com.wak.entities.inventory.InventoryDetail;
import com.wak.enums.TccEnum;
import com.wak.inventory.mapper.InventoryDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.wak.inventory.mapper.InventoryMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuankang
 * @date 2023/11/17 11:55
 * @Description TODO
 * @Version 1.0
 */
@Service
@Slf4j
public class InventoryService {

    @Resource
    private InventoryMapper inventoryMapper;

    @Resource
    private InventoryDetailMapper inventoryDetailMapper;

    @Transactional(rollbackFor = Exception.class)
    public void decrease(InventoryDTO inventoryDTO) {
        //幂等校验
        checkIdempotent(inventoryDTO);
        //保存详细数据
        saveDetailInfo(inventoryDTO);
        //库存扣减
        updateInventoryCount(inventoryDTO);
    }


    /**
     * 扣除库存数量
     *
     * @param inventoryDTO 库存dto
     */
    private void updateInventoryCount(InventoryDTO inventoryDTO) {
        int updateResult = inventoryMapper.updateInventoryCountByProductId(inventoryDTO.getProductCount(), inventoryDTO.getProductId());
        if (updateResult <= 0) {
            throw new RuntimeException("库存扣减失败，updateResult:" + updateResult);
        }
    }

    /**
     * 保存详细信息
     *
     * @param inventoryDTO 库存dto
     */
    private void saveDetailInfo(InventoryDTO inventoryDTO) {
        InventoryDetail inventoryDetail = BeanUtil.copyProperties(inventoryDTO, InventoryDetail.class);
        inventoryDetailMapper.insertSelective(inventoryDetail);
    }

    /**
     * 幂等校验
     *
     * @param inventoryDTO inventoryDTO
     */
    private void checkIdempotent(InventoryDTO inventoryDTO) {
        int count = inventoryDetailMapper.countByOrderNo(inventoryDTO.getOrderNo());
        if (count > 0) {
            throw new RuntimeException("订单已存在...");
        }
    }

    /**
     * 检查存货数量
     *
     * @param inventoryDTO 库存dto
     */
    private void checkInventoryCount(InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryMapper.selectByPrimaryKey(inventoryDTO.getProductId());
        if (ObjUtil.isEmpty(inventory)) {
            throw new RuntimeException("无该订单的商品...");
        }
        if (inventory.getTotalInventory() <= 0 || inventory.getTotalInventory() < inventoryDTO.getProductCount()) {
            throw new RuntimeException("库存不足...");
        }
    }
}
