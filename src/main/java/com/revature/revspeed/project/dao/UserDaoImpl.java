package com.revature.revspeed.project.dao;
import com.revature.revspeed.project.dbconnection.Dbconnection;
import com.revature.revspeed.project.model.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    // SQL queries
    private static final String CREATE_USER_QUERY = "INSERT INTO user (username, password, phone_no, email, address) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM user WHERE user_id = ?";
    private static final String GET_USER_BY_USERNAME_PASSWORD_QUERY = "SELECT * FROM user WHERE username = ? AND password = ?";
   // private static final String GET_ALL_USERS_QUERY = "SELECT * FROM user";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET username = ?, password = ?, phone_no = ?, email = ?, address = ? WHERE user_id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE user_id = ?";
    private static final String CHECK_USERNAME_EXISTENCE_QUERY = "SELECT * FROM user WHERE username = ?";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";



    @Override
    public boolean createUser(User user) {
        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            if (!isValidPassword(user.getPassword()) || !isValidPhoneNumber(user.getPhoneNo()) || isUsernameExists(user.getUsername())) {
                return false;
            }

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhoneNo());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getAddress());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                    return true;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean isValidPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH && matchesPattern(password);
    }


    private boolean matchesPattern(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }


    private boolean isUsernameExists(String username) {
        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USERNAME_EXISTENCE_QUERY)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public User getUserById(int userId) {
        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID_QUERY)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME_PASSWORD_QUERY)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
@Override
    public List<User> UserProfile(int userId) {
        List<User> userList = new ArrayList<>();
        String GET_USER_PROFILE_QUERY = "SELECT * FROM user WHERE user_id = ?";

        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_PROFILE_QUERY)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userList.add(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }


    @Override
    public void updateUser(User user) {
        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhoneNo());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setInt(6, user.getUserId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    @Override
    public void deleteUser(int userId) {
        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)) {
            preparedStatement.setInt(1, userId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setPhoneNo(resultSet.getString("phone_no"));
        user.setEmail(resultSet.getString("email"));
        user.setAddress(resultSet.getString("address"));
        return user;
    }
}
