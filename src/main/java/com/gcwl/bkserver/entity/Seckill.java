package com.gcwl.bkserver.entity;

import java.io.Serializable;
import java.util.Date;

public class Seckill implements Serializable {

    //定义一个序列号
    private static final long serialVersionUID = 1L;

    private int sid;
    private String goodsCode;
    private double seckillPrice;
    private int counts;
    private Date startTime;
    private Date endTime;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
