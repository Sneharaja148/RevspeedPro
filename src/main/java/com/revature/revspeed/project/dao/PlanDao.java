package com.revature.revspeed.project.dao;

import com.revature.revspeed.project.model.Plan;

import java.util.List;
// PlanDao.java
import java.util.List;

public interface PlanDao {
    List<Plan> viewPlans() throws Exception;
    Plan getPlanById(int planId) throws Exception;


}
