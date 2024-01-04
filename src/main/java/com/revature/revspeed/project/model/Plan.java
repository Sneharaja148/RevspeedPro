package com.revature.revspeed.project.model;

// Plan.java
public class Plan {
    private int planID;
    private String planName;
    private String price;
    private String dataAllowance;
    private int validity;
    private String talktime;
    private String smsAllowance;

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "planID=" + planID +
                ", planName='" + planName + '\'' +
                ", price='" + price + '\'' +
                ", dataAllowance='" + dataAllowance + '\'' +
                ", validity=" + validity +
                ", talktime='" + talktime + '\'' +
                ", smsAllowance='" + smsAllowance + '\'' +
                '}';
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDataAllowance() {
        return dataAllowance;
    }

    public void setDataAllowance(String dataAllowance) {
        this.dataAllowance = dataAllowance;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getTalktime() {
        return talktime;
    }

    public void setTalktime(String talktime) {
        this.talktime = talktime;
    }

    public String getSmsAllowance() {
        return smsAllowance;
    }

    public void setSmsAllowance(String smsAllowance) {
        this.smsAllowance = smsAllowance;
    }

    public Plan(int planID, String planName, String price, String dataAllowance, int validity, String talktime, String smsAllowance) {
        this.planID = planID;
        this.planName = planName;
        this.price = price;
        this.dataAllowance = dataAllowance;
        this.validity = validity;
        this.talktime = talktime;
        this.smsAllowance = smsAllowance;
    }

    public Plan() {

    }


}





