package com.imooc.pojo.vo;

import java.util.Date;

/**
 * @author
 * @create 2020-09-01-10:10
 */
public class MyCommentVo {

    private  String commentId;
    private  String content;
    private  Date createdTime;
    private  String itemId;
    private  String itemName;
    private  String sepcName;
    private  String itemImg;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSepcName() {
        return sepcName;
    }

    public void setSepcName(String sepcName) {
        this.sepcName = sepcName;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }
}
