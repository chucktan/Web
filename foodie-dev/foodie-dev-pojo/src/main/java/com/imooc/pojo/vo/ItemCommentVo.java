package com.imooc.pojo.vo;

import java.util.Date;

/**
 * 用于展示商品评价的VO
 */
public class ItemCommentVo {

    private  Integer commentLevel;
    private  String content;
    private  String specName;
    private  Date createdTime;
    private  String userFace;
    private  String nickName;

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpecName() {
        return specName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }


    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
