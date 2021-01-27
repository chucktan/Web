package com.imooc.pojo.vo;

import com.imooc.pojo.bo.ShopcartBo;

import java.util.List;

/**
 * @author
 * @create 2020-07-24-9:26
 */
public class OrderVo {

    private  String orderId;
    private  MerchantOrdersVo merchantOrdersVo;
    private List<ShopcartBo> toBeRemovedShopcartList;


    public List<ShopcartBo> getToBeRemovedShopcartList() {
        return toBeRemovedShopcartList;
    }

    public void setToBeRemovedShopcartList(List<ShopcartBo> toBeRemovedShopcartList) {
        this.toBeRemovedShopcartList = toBeRemovedShopcartList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVo getMerchantOrdersVo() {
        return merchantOrdersVo;
    }

    public void setMerchantOrdersVo(MerchantOrdersVo merchantOrdersVo) {
        this.merchantOrdersVo = merchantOrdersVo;
    }
}
