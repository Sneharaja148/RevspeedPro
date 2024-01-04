package com.revature.revspeed.project.dao;
import com.revature.revspeed.project.dbconnection.Dbconnection;
import com.revature.revspeed.project.model.Plan;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;


public class PlanDaoImpl implements PlanDao {

    public List<Plan> viewPlans() throws Exception {
        List<Plan> plans = new ArrayList<>();
        String query = "SELECT * FROM plans";

        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Plan plan = new Plan();
                plan.setPlanID(resultSet.getInt("planID"));
                plan.setPlanName(resultSet.getString("planName"));
                plan.setPrice(resultSet.getString("price"));
                plan.setDataAllowance(resultSet.getString("dataAllowance"));
                plan.setValidity(resultSet.getInt("validity"));
                plan.setTalktime(resultSet.getString("talktime"));
                plan.setSmsAllowance(resultSet.getString("smsAllowance"));

                plans.add(plan);
            }
            }catch (SQLException e) {
            e.printStackTrace();
        }

        return plans;
    }
    @Override
    public Plan getPlanById(int planId) throws Exception {
        String query = "SELECT * FROM plans WHERE planID = ?";
        Plan plan = null;

        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, planId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    plan = new Plan();
                    plan.setPlanID(resultSet.getInt("planID"));
                    plan.setPlanName(resultSet.getString("planName"));
                    plan.setPrice(resultSet.getString("price"));
                    plan.setDataAllowance(resultSet.getString("dataAllowance"));
                    plan.setValidity(resultSet.getInt("validity"));
                    plan.setTalktime(resultSet.getString("talktime"));
                    plan.setSmsAllowance(resultSet.getString("smsAllowance"));
                }
            }
        }

        return plan;
    }




}

