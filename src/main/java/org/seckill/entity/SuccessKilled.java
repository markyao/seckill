package org.seckill.entity;

import java.util.Date;

public class SuccessKilled {

    private long seckillId;

    private String userPhone;

    private short state;

    private Date createTIme;


    private Seckill seckill;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTIme() {
        return createTIme;
    }

    public void setCreateTIme(Date createTIme) {
        this.createTIme = createTIme;
    }


    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone='" + userPhone + '\'' +
                ", state=" + state +
                ", createTIme=" + createTIme +
                '}';
    }
}
