package com.tytsmile.miniaccount.databases.entity;

import java.sql.Date;

public class Record {
    private int id;//序号
    private double money;//金额
    private String classify;//分类
    private String comment;//备注
    private Date date;//时间



    public Record() {
    }
    public Record(int id,double money,String classify, String comment, Date date) {
        this.id=id;
        this.money=money;
        this.classify=classify;
        this.comment=comment;
        this.date=date;
    }
    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", money=" + money +
                ", classify=" + classify +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}


