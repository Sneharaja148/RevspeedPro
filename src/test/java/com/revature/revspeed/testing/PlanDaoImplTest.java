//package com.revature.revspeed.testing;
//import com.revature.revspeed.project.dao.PlanDao;
//import com.revature.revspeed.project.dao.PlanDaoImpl;
//import com.revature.revspeed.project.dbconnection.Dbconnection;
//import com.revature.revspeed.project.model.Plan;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//
//import org.mockito.Mock;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.*;
//import org.mockito.MockitoAnnotations;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//public class PlanDaoImplTest {
//
//    @Test
//    public void testViewPlans() throws Exception {
//        // Create a mock for the PlanDao interface
//        PlanDao planDaoMock = mock(PlanDao.class);
//
//        // Create some sample plans
//        Plan plan1 = new Plan(1, "Titanium Plan", "₹1299", "6 GB", 30, "Unlimited", "600 SMS");
//        Plan plan2 = new Plan(2, "Bronze Plan", "₹199", "1 GB", 30, "Unlimited", "100 SMS");
//
//        // Define the behavior of the mock when viewPlans() is called
//        when(planDaoMock.viewPlans()).thenReturn(Arrays.asList(plan1, plan2));
//
//        // Create an instance of PlanDaoImpl and pass the mock to it
//        PlanDaoImpl planDaoImpl = new PlanDaoImpl(planDaoMock);
//
//        // Call the viewPlans method on PlanDaoImpl
//        List<Plan> resultPlans = planDaoImpl.viewPlans();
//
//        // Verify that the method was called on the mock
//        verify(planDaoMock).viewPlans();
//
//        // Verify the expected plans are returned
//        assertEquals(2, resultPlans.size());
//        assertEquals(plan1, resultPlans.get(0));
//        assertEquals(plan2, resultPlans.get(1));
//    }
//}
