package com.wak.inventory.service;

import com.wak.entities.InventoryDetail;
import com.wak.enums.TccEnum;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.wak.inventory.mapper.InventoryDetailMapper;
/**
 * @author wuankang
 * @date 2023/11/21 11:13
 * @Description TODO
 * @Version 1.0
 */
@Service
public class InventoryDetailService{

    @Resource
    private InventoryDetailMapper inventoryDetailMapper;

    /**
     * 保存详细信息
     *
     * @param detail 细节
     * @return {@code Integer}
     */
    public Integer saveDetailInfo(InventoryDetail detail){
        detail.setTxStatus(TccEnum.TRY.getCode());
        return inventoryDetailMapper.insert(detail);
    }
}
