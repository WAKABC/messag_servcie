package com.wak.inventory.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.wak.entities.Inventory;
import com.wak.entities.InventoryDTO;
import com.wak.entities.InventoryDetail;
import com.wak.enums.TccEnum;
import com.wak.inventory.mapper.InventoryDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
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

    @Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    @Transactional(rollbackFor = Exception.class)
    public String decrease(InventoryDTO inventoryDTO) {
        log.info("-------进入库存的try-------");
        //判断库存是否充足
        checkInventoryCount(inventoryDTO);
        //幂等校验
        checkIdempotent(inventoryDTO);
        //保存详细数据
        saveDetailInfo(inventoryDTO);
        //库存扣减
        updateInventoryCount(inventoryDTO);
        return "decrease ok";
    }

    /**
     * Hmily确认方法
     *
     * @param inventoryDTO 库存dto
     * @return {@code String}
     */
    @Transactional(rollbackFor = Exception.class)
    public String confirm(InventoryDTO inventoryDTO){
        //幂等
        checkIdempotent(inventoryDTO);
        //修改订单状态
        updateStatus(TccEnum.CONFIRM, inventoryDTO);
        //付款后，解除去掉冻结的库存
        releaseLockInventory(inventoryDTO);
        return "confirm ok";
    }

    /**
     * hmily取消方法
     *
     * @param inventoryDTO 库存dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancel(InventoryDTO inventoryDTO){
        //幂等
        checkIdempotent(inventoryDTO);
        //修改订单状态
        updateStatus(TccEnum.CANCEL, inventoryDTO);
        //恢复冻结和扣除的库存
        recoverOriginalInventoryCount(inventoryDTO);
    }

    /**
     * 释放冻结的库存
     *
     * @param inventoryDTO 库存dto
     * @return int
     */
    private int releaseLockInventory(InventoryDTO inventoryDTO) {
        return inventoryMapper.updateDecLockInventoryByProductid(inventoryDTO.getCount(), inventoryDTO.getProductId());
    }

    /**
     * 更新订单状态
     *
     * @param tccEnum       状态枚举
     * @param inventoryDTO 库存dto
     * @return int
     */
    private int updateStatus(TccEnum tccEnum, InventoryDTO inventoryDTO) {
        return inventoryDetailMapper.updateTxStatusByProductId(tccEnum.getCode(), inventoryDTO.getOrderNo());
    }

    /**
     * 恢复原来库存数量
     *
     * @param inventoryDTO 库存dto
     * @return int
     */
    private void recoverOriginalInventoryCount(InventoryDTO inventoryDTO) {
        inventoryMapper.updateInventoryCountByProductId(Math.negateExact(inventoryDTO.getCount()), inventoryDTO.getProductId());
    }

    /**
     * 扣除库存数量
     *
     * @param inventoryDTO 库存dto
     * @return int
     */
    private int updateInventoryCount(InventoryDTO inventoryDTO) {
        return inventoryMapper.updateInventoryCountByProductId(inventoryDTO.getCount(), inventoryDTO.getProductId());
    }

    /**
     * 保存详细信息
     *
     * @param inventoryDTO 库存dto
     */
    private void saveDetailInfo(InventoryDTO inventoryDTO) {
        InventoryDetail inventoryDetail = BeanUtil.copyProperties(inventoryDTO, InventoryDetail.class);
        inventoryDetail.setTxStatus(TccEnum.TRY.getCode());
        inventoryDetailMapper.insert(inventoryDetail);
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
        if (inventory.getTotalInventory() <= 0 || inventory.getTotalInventory() < inventoryDTO.getCount()) {
            throw new RuntimeException("库存不足...");
        }
    }
}
