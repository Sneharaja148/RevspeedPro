package com.revature.revspeed.project;
import com.revature.revspeed.project.dao.*;
import com.revature.revspeed.project.model.Plan;
import com.revature.revspeed.project.model.Subscription;
import com.revature.revspeed.project.model.User;
import java.util.List;
import java.util.Scanner;
import static com.revature.revspeed.project.dao.UserDaoImpl.isValidPhoneNumber;
import java.util.InputMismatchException;
import com.revature.revspeed.project.dao.PlanDaoImpl;
import com.revature.revspeed.project.dao.PlanDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;



public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    static {

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);

    }
    public static User loggedInUser;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDao userDao = new UserDaoImpl();
        PlanDao planDao = new PlanDaoImpl();
        SubscriptionDao subscriptionDao = new SubscriptionDaoImpl();

        // Display welcome message
        displayWelcomeMessage();

        int mainChoice;
        do {
            // Display main menu based on user login status
            logger.info("Displaying main menu...");
            if (loggedInUser == null) {
                displayMainMenuForGuest();
            } else {
                displayMainMenuForLoggedInUser();
            }

            System.out.print("Enter your choice: ");
            try {
                mainChoice = scanner.nextInt();
                scanner.nextLine();

                switch (mainChoice) {
                    case 1:
                        if (loggedInUser == null) {
                            logger.info("User chose to login.");
                            login(scanner, userDao);
                        } else {
                            logger.info("User chose to view subscriptions.");

                            viewSubscriptions(subscriptionDao,planDao);
                        }
                        break;
                    case 2:
                        if (loggedInUser == null) {

                            logger.info("User chose to register.");
                            registerUser(scanner, userDao);
                        } else {

                            logger.info("User chose to subscribe to a plan.");
                            subscribeToPlan(scanner, subscriptionDao, planDao);
                        }
                        break;
                    case 3:
                        if (loggedInUser != null) {
                            // For logged-in users, user management
                            userManagement(scanner, userDao);
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                        break;
                    case 4:
                        if (loggedInUser != null) {
                            // For logged-in users, logout
                            loggedInUser = null;
                            System.out.println("Logout successful! Returning to the Main Menu.");
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                        break;
                    default:
                        logger.warn("Invalid choice. Please try again.");
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer choice.");
                scanner.nextLine();
                mainChoice = -1;
            }
        } while (mainChoice != 4);
        logger.info("Closing the application.");

        scanner.close();
    }


    private static void displayWelcomeMessage() {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("                        Welcome to RevSpeed!                       ");
        System.out.println("                       ======================                       ");
        System.out.println(" ");
    }

    private static void displayMainMenuForGuest() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    }

    private static void displayMainMenuForLoggedInUser() {
        System.out.println("\nMain Menu:");
        System.out.println("1. View Subscriptions");
        System.out.println("2. Subscribe to Plan");
        System.out.println("3. User Management");
        System.out.println("4. Logout");
        System.out.println("5. Opt-out of Subscription");

    }

    private static void userManagement(Scanner scanner, UserDao userDao) {
        // Check if a user is logged in
        if (loggedInUser == null) {
            System.out.println("Please login first to access user management.");
            return;
        }

        int userChoice;
        do {
            // Display user management menu
            displayUserManagementMenu();

            System.out.print("Enter your choice: ");
            userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    // View user profile

                    int userId = loggedInUser.getUserId();  // Get the ID of the logged-in user

// Call UserProfile method with the logged-in user's ID
                    List<User> userList = userDao.UserProfile(userId);

                    System.out.println("\nUser Profile:");
                    System.out.printf("%-15s %-20s %-15s %-30s %-20s%n", "User ID", "Username", "Phone Number", "Email", "Address");
                    System.out.println("---------------------------------------------------------------------------------------------");

                    for (User user : userList) {
                        System.out.printf("%-15d %-20s %-15s %-30s %-20s%n",
                                user.getUserId(),
                                user.getUsername(),
                                user.getPhoneNo(),
                                user.getEmail(),
                                user.getAddress());
                    }

                    break;
                case 2:
                    // Update own details
                    updateUserDetails(scanner, userDao);
                    break;
                case 3:
                    // Delete own account
                    deleteUserAccount(userDao);
                    break;
                case 4:
                    // Logout
                    loggedInUser = null;
                    System.out.println("Logout successful. Returning to the Main Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (userChoice != 4);
    }

    private static void displayUserManagementMenu() {
        System.out.println("\nUser Management Menu:");
        System.out.println("1. User profile");
        System.out.println("2. Update User Details");
        System.out.println("3. Delete Account");
        System.out.println("4. Logout");
    }

    private static void updateUserDetails(Scanner scanner, UserDao userDao) {
        // Display current user details
        System.out.println("Current User Details:");
        System.out.println(loggedInUser);

        // Get updated user input
        User updatedUser = getUserInput(scanner);

        // Set the user ID to the logged-in user's ID
        updatedUser.setUserId(loggedInUser.getUserId());

        // Update user details
        userDao.updateUser(updatedUser);

        System.out.println("User details updated successfully!");
        loggedInUser = userDao.getUserById(loggedInUser.getUserId()); // Refresh the logged-in user object
    }

    private static void deleteUserAccount(UserDao userDao) {
        // Display current user details
        System.out.println("Current User Details:");
        System.out.println(loggedInUser);

        // Confirm account deletion
        System.out.print("Are you sure you want to delete your account? (yes/no): ");
        Scanner scanner = new Scanner(System.in);
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes")) {
            userDao.deleteUser(loggedInUser.getUserId());
            System.out.println("Account deleted successfully. Goodbye!");
            loggedInUser = null;
        } else {
            System.out.println("Account deletion canceled. Returning to User Management Menu.");
        }
    }

    private static void registerUser(Scanner scanner, UserDao userDao) {
        User newUser = getUserInput(scanner);
        boolean registrationSuccess = userDao.createUser(newUser);

        if (registrationSuccess) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Invalid password or username. Please try again.");
        }
        // Validate phone number
        if (!isValidPhoneNumber(newUser.getPhoneNo())) {
            System.out.println("Invalid phone number. Please enter a valid 10-digit phone number.");

            // Exit the method if phone number is invalid
        }

    }

    private static void login(Scanner scanner, UserDao userDao) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = userDao.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            loggedInUser = user;
            System.out.println("Login successful! Welcome, " + loggedInUser.getUsername() + "!");
        } else {
            System.out.println("Login failed. Please check your username and password.");
        }
    }

    private static User getUserInput(Scanner scanner) {
        User user = new User();

        // Autogenerated userId will be set by the UserDaoImpl during user creation

        System.out.print("Enter Username: ");
        user.setUsername(scanner.nextLine());
        System.out.print("Enter Password: ");
        user.setPassword(scanner.nextLine());
        System.out.print("Enter Phone Number: ");
        user.setPhoneNo(scanner.nextLine());
        System.out.print("Enter Email: ");
        user.setEmail(scanner.nextLine());
        System.out.print("Enter Address: ");
        user.setAddress(scanner.nextLine());

        return user;
    }
    public static Plan findPlanById(List<Plan> plans, int planID) {
        for (Plan plan : plans) {
            if (plan.getPlanID() == planID) {
                return plan;
            }
        }
        return null;
    }

    private static void viewPlans(PlanDao planDao) {
        try {
            List<Plan> plans = planDao.viewPlans();

            if (plans.isEmpty()) {
                System.out.println("No plans available.");
            } else {
                // Display plans in a table-like format
                System.out.printf("%-10s%-15s%-15s%-15s%-15s%-15s%-15s%n",
                        "Plan ID", "Plan Name", "Price", "Data Allowance", "Validity", "Talktime", "SMS Allowance");
                System.out.println("----------------------------------------------------------------------------------------------------");

                for (Plan plan : plans) {
                    System.out.printf("%-10d%-15s%-15s%-15s%-15s%-15s%-15s%n",
                            plan.getPlanID(), plan.getPlanName(), plan.getPrice(),
                            plan.getDataAllowance(), plan.getValidity(), plan.getTalktime(), plan.getSmsAllowance());
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving plans: " + e.getMessage());
        }
    }

    public static void viewSubscriptions(SubscriptionDao subscriptionDao, PlanDao planDao) {
        if (loggedInUser != null) {
            try {
                List<Subscription> subscriptions = subscriptionDao.getUserSubscriptions(loggedInUser.getUserId());
                if (subscriptions.isEmpty()) {
                    System.out.println("\nYou have no subscriptions.");
                } else {
                    // Display subscriptions in a table-like format
                    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s%n", "Subscription ID", "User ID", "Plan ID", "Plan Name", "Start Date", "End Date");
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------");
                    for (Subscription subscription : subscriptions) {
                        Plan plan = findPlanById(planDao.viewPlans(), subscription.getPlanID());
                        if (plan != null) {
                            System.out.printf("%-20d %-20d %-20d %-20s %-20s %-20s%n",
                                    subscription.getSubscriptionId(),
                                    subscription.getUserId(),
                                    subscription.getPlanID(),
                                    plan.getPlanName(),
                                    subscription.getStartDate(),  // Include the start date here
                                    subscription.getEndDate());  // Include the end date here

                }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error retrieving subscriptions: " + e.getMessage());
            }
        } else {
            System.out.println("Please login to view subscriptions.");
        }
    }


    private static void subscribeToPlan(Scanner scanner, SubscriptionDao subscriptionDao, PlanDao planDao) {
        // Check if a user is logged in
        if (loggedInUser == null) {
            System.out.println("Please login first to subscribe to a plan.");
            return;
        }

        // Display available plans
        viewPlans(planDao);

        // Subscription
        System.out.print("Enter the Plan ID you want to subscribe to: ");
        int planID = scanner.nextInt();

        try {
            Plan selectedPlan = findPlanById(planDao.viewPlans(), planID);
            if (selectedPlan == null) {
                System.out.println("Invalid Plan ID. Returning to the Main Menu.");
            } else {

                Subscription subscription = new Subscription();
                subscription.setUserId(loggedInUser.getUserId());
                subscription.setPlanID(selectedPlan.getPlanID());

                subscriptionDao.createSubscription(subscription);

                // Display subscription confirmation message
                System.out.println("Subscription successful! You are now subscribed to the " + selectedPlan.getPlanName() + ".");
            }
        } catch (Exception e) {
            System.out.println("Error subscribing to the plan: " + e.getMessage());
        }
    }



}
