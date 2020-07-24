package com.imooc.pojo.vo;

/**
 * @author
 * @create 2020-07-24-9:26
 */
public class OrderVo {

    private  String orderId;
    private  MerchantOrdersVo merchantOrdersVo;

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
