package com.wak.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.wak.entities.coupon.CouponDTO;
import com.wak.entities.inventory.Inventory;
import com.wak.entities.inventory.InventoryDTO;
import com.wak.entities.msg.MsgDTO;
import com.wak.entities.order.Order;
import com.wak.entities.order.OrderDTO;
import com.wak.entities.score.ScoreDTO;
import com.wak.enums.MsgEnum;

import java.math.BigDecimal;

/**
 * @author wuankang
 * @date 2023/11/19 14:09
 * @Description TODO
 * @Version 1.0
 */
public class AssembleObjUtil {

    /**
     * 组装订单对象
     *
     * @param couponNo 优惠券编号
     * @return {@code Order}
     */
    public static Order assemblyOrder(Integer couponNo) {
        Order order = new Order();
        order.setUserId(1);
        order.setProductId(1);
        order.setPaymentMoney(new BigDecimal(100));
        order.setOrderNo(IdUtil.simpleUUID());
        order.setProductCount(1);
        order.setDecreaseScore(2);
        order.setCouponReceiveId(couponNo);
        return order;
    }

    /**
     * 组装消息dto
     *
     * @param msgEnum  消息枚举
     * @param appName 应用程序名称
     * @param order   订单
     * @return {@code MsgDTO}
     */
    public static MsgDTO assemblyMsgDto(MsgEnum msgEnum, String appName, Order order) {
        MsgDTO msgDTO = new MsgDTO();
        String msgId = msgEnum.getCode() + "-" + order.getOrderNo();
        msgDTO.setMsgId(msgId);
        msgDTO.setAppName(appName);
        msgDTO.setRoutingKey(msgEnum.getMsgTopic());
        msgDTO.setJsonMsg(getJsonMsg(msgEnum, order));
        return msgDTO;
    }

    /**
     * 获取消息json字符串
     *
     * @param msgEnum 消息枚举
     * @param order  订单
     * @return {@code String}
     */
    private static String getJsonMsg(MsgEnum msgEnum, Order order) {
        String jsonMsg;
        switch (msgEnum.getCode()) {
            case 1:
                jsonMsg = JSONUtil.toJsonStr(BeanUtil.copyProperties(order, OrderDTO.class));
                break;
            case 2:
                jsonMsg = JSONUtil.toJsonStr(BeanUtil.copyProperties(order, ScoreDTO.class));
                break;
            case 3:
                jsonMsg = JSONUtil.toJsonStr(BeanUtil.copyProperties(order, InventoryDTO.class));
                break;
            case 4:
                jsonMsg = JSONUtil.toJsonStr(BeanUtil.copyProperties(order, CouponDTO.class));
                break;
            default:
                jsonMsg = "null";
        }
        return jsonMsg;
    }


}
