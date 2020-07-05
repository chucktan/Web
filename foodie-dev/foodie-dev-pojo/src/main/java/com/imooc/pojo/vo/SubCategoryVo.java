package com.imooc.pojo.vo;

/**
 * 二级分类VO
 */
public class SubCategoryVo {

    private  Integer subId;
    private  String subName;
    private  String subType;
    private  String subFatherId;

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(String subFatherId) {
        this.subFatherId = subFatherId;
    }
}
