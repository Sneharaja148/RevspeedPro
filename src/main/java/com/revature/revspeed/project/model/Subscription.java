package com.revature.revspeed.project.model;

import java.time.LocalDate;
import java.util.Date;

public class Subscription {
    private int subscriptionId;
    private int userId;
    private int planID;  // Updated variable name

      // in days
    private LocalDate startDate;
    private Date endDate;

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "subscriptionId=" + subscriptionId +
                ", userId=" + userId +
                ", planID=" + planID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Subscription(int subscriptionId, int userId, int planID, LocalDate startDate, Date endDate) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.planID = planID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Subscription() {

    }


// Constructors, getters, and setters
}
