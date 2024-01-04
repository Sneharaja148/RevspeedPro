package com.revature.revspeed.testing;

import com.revature.revspeed.project.dao.UserDao;
import com.revature.revspeed.project.dao.UserDaoImpl;
import com.revature.revspeed.project.dbconnection.Dbconnection;
import com.revature.revspeed.project.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.*;


public class UserTest {
    @Mock
    private UserTest dao;

    public static void main(String[] args) throws SQLException {
        new UserTest().testGetUser();
    }
    public void testGetUser() throws SQLException{
        dao = Mockito.mock(UserTest.class);
        MockitoAnnotations.openMocks(this);
      User user= getUsername();
      Mockito.when(user.getUsername()).thenReturn(("sneha"));
        System.out.println(user.getUsername());
    }
    private User getUsername(){
        User user = new User();
        user.setUsername("Aishu");
        user.setPassword("Aishu@10");
        user.setPhoneNo("9876723001");
        user.setEmail("aishu@gmail.com");
        user.setAddress("chennai");
        return user;
    }
}
