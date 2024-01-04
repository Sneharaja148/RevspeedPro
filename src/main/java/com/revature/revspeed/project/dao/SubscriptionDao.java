package com.revature.revspeed.project.dao;

import com.revature.revspeed.project.model.Subscription;
import com.revature.revspeed.project.model.User;

import java.sql.SQLException;
import java.util.List;

import java.util.List;

public interface SubscriptionDao {
    void createSubscription(Subscription subscription) throws Exception;
    List<Subscription> getUserSubscriptions(int userId) throws Exception;

}



