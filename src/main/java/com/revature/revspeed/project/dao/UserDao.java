package com.revature.revspeed.project.dao;

import com.revature.revspeed.project.model.User;

import java.util.List;

public interface UserDao {
    boolean createUser(User user);
    User getUserById(int userId);
    User getUserByUsernameAndPassword(String username, String password);

    void updateUser(User user);
    void deleteUser(int userId);

    List<User> UserProfile(int userId);
}
