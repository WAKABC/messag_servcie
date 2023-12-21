package com.wak.tx.pay.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.wak.entities.order.Order;
import com.wak.entities.pay.Pay;
import com.wak.entities.pay.PayDTO;
import com.wak.api.OrderApi;
import com.wak.tx.pay.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuankang
 * @version 1.0.0
 * @date 2023/12/10
 * @description TODO
 */
@Controller
public class PayController {

    @Resource
    private OrderApi orderApi;

    /**
     * 服务对象
     */
    @Resource
    private PayService payService;

    /**
     * 获取订单列表
     * 第一步，查找所有的订单信息, 订单list页面在orderList.html
     * 默认状态下，在该页面的最右侧，点击【支付】按钮会调用createPayForm
     * 方法并跳转到payForm.html页面
     *
     * @param modelMap 模型图
     * @return {@code String}
     */
    @GetMapping("/orderList")
    public String getOrderList(ModelMap modelMap) {
        List<Order> orders = orderApi.orderList();
        if (orders != null) {
            modelMap.addAttribute("orderList", orders);
        }
        return "orderList";
    }

    /**
     * 创建支付表单
     * 第二步，跳转到支付方式选择页面payForm.html，选择任意一种支付方式(余额、微信、支付宝)
     *
     * @param orderNo 订单号，不是支付号
     * @return {@code String}
     */
    @GetMapping("/pay/create/{orderNo}")
    public String createPayForm(@PathVariable String orderNo, ModelMap modelMap) {
        //幂等
        Pay pay = payService.findByOrderNo(orderNo);
        if (ObjUtil.isNotEmpty(pay)) {
            //tx_pay表里面的`status`字段， 支付状态 -1：取消 0 未完成 1已完成 -2:异常'
            if (pay.getStatus().byteValue() == 0) {
                return "redirect:/pay/confirmPay/" + pay.getPayNo();
            } else if (pay.getStatus().byteValue() == 1) {
                throw new RuntimeException("已经付款成功");
            } else {
                throw new RuntimeException("抛出其它支付异常-1 or -2");
            }
        }
        //如果pay表中没有，则去order中获取订单数据
        Order order = orderApi.findOrderByOrderNo(orderNo);
        PayDTO payDTO = new PayDTO();
        payDTO.setOrderNo(order.getOrderNo());
        payDTO.setAmount(order.getPaymentMoney());
        payDTO.setUserId(order.getUserId());
        modelMap.addAttribute("pay", payDTO);
        return "payForm";
    }

    /**
     * 第三步，在当前支付页面payForm.html择任意一种支付方式(余额、微信、支付宝)点击【去支付按钮】
     * 就跳转进入支付二维码payConfirmForm.html
     *
     * @param payDTO 支付dto
     * @return {@code String}
     */
    @PostMapping("/pay/create")
    public String create(@ModelAttribute PayDTO payDTO) {
        //幂等
        Pay pay = payService.findByOrderNo(payDTO.getOrderNo());
        //再判断是否已支付tx_pay表里status字段是否为零,- 支付状态 -1：取消 0 未完成 1已完成 -2:异常
        if (ObjUtil.isNotEmpty(pay)) {
            if (pay.getStatus().byteValue() == 0) {
                return "redirect:/pay/confirmPay/" + pay.getPayNo();
            } else if (pay.getStatus().byteValue() == 1) {
                //已支付:提示，已支付
                throw new RuntimeException("已付款成功！！");
            } else {
                //其它情况抛出异常
                throw new RuntimeException("抛出支付异常！！");
            }
        }
        //如果pay为null
        String payNo = payService.createPay(payDTO);
        return "redirect:/pay/confirmPay/" + payNo;
    }

    /**
     * 第四步，支付订单创建已成功(tx_pay表已有记录)，跳转进入支付二维码payConfirmForm.html该接口是一个渲染页面接口
     *
     * @param payNo    支付编号
     * @param modelMap 模型图
     * @return {@code String}
     */
    @GetMapping("/pay/confirmPay/{payNo}")
    public String confirmPayForm(@PathVariable String payNo, ModelMap modelMap) {
        Pay pay = payService.findByPayNo(payNo);
        PayDTO payDTO = BeanUtil.copyProperties(pay, PayDTO.class);
        modelMap.addAttribute("pay", payDTO);
        return "payConfirmForm";
    }

    /**
     * 确认支付
     * 第五步模拟支付
     * @param payNo 支付编号
     * @return {@code String}
     */
    @GetMapping("/confirmPay")
    public String confirmPay(String payNo){
        this.payService.confirmPayHandle(payNo);
        return "redirect:orderList";
    }

}
