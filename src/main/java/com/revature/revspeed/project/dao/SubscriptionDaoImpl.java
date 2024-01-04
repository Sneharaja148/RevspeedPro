package com.revature.revspeed.project.dao;

import com.revature.revspeed.project.dbconnection.Dbconnection;
import com.revature.revspeed.project.model.Plan;
import com.revature.revspeed.project.model.Subscription;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SubscriptionDaoImpl implements SubscriptionDao {
    private static final String CREATE_SUBSCRIPTION_QUERY = "INSERT INTO subscriptions (user_id, planID, start_date, end_date) VALUES (?, ?, ?, ?)";
    private static final String GET_USER_SUBSCRIPTIONS_QUERY = "SELECT * FROM subscriptions WHERE user_id = ?";

    @Override
    public void createSubscription(Subscription subscription) throws Exception {
        String query = "INSERT INTO subscriptions (user_id, planID, start_date, end_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, subscription.getUserId());
            preparedStatement.setInt(2, subscription.getPlanID());

            // Automatically set the start date to the current date and time
            java.util.Date currentDate = new java.util.Date();
            preparedStatement.setTimestamp(3, new Timestamp(currentDate.getTime()));

            // Calculate end date based on the validity of the plan
            PlanDao planDao = new PlanDaoImpl();
            Plan selectedPlan = planDao.getPlanById(subscription.getPlanID());
            java.util.Date endDate = calculateEndDate(currentDate, selectedPlan.getValidity());

            preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating subscription failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subscription.setSubscriptionId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating subscription failed, no ID obtained.");
                }
            }
        }
    }

    private java.util.Date calculateEndDate(Date startDate, int validity) {
        // Implement the logic to calculate the end date based on the validity of the plan
        // For example, you can use the java.time package for date manipulation

        // Here's a simple example assuming validity is in days
        int validityDays = validity;
        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndDate = localStartDate.plusDays(validityDays);

        return Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    @Override
    public List<Subscription> getUserSubscriptions(int userId) throws Exception {
        List<Subscription> subscriptions = new ArrayList<>();

        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_SUBSCRIPTIONS_QUERY)) {

            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Subscription subscription = mapResultSetToSubscription(resultSet);
                    subscriptions.add(subscription);
                }
            }
        }

        return subscriptions;
    }

    private Subscription mapResultSetToSubscription(ResultSet resultSet) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionId(resultSet.getInt("subscription_id"));
        subscription.setUserId(resultSet.getInt("user_id"));
        subscription.setPlanID(resultSet.getInt("planID"));
        subscription.setStartDate(resultSet.getDate("start_date").toLocalDate());
        subscription.setEndDate(resultSet.getDate("end_date"));
        return subscription;
    }


}
