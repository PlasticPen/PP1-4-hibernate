package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Create DAO instance
        UserServiceImpl userService = new UserServiceImpl();
        //Create table
        userService.createUsersTable();
        //Add users to DB and print out their names
        saveAndPrint(userService, "Alan", "Wake", 30);
        saveAndPrint(userService, "Bob", "Neilson", 24);
        saveAndPrint(userService, "Drake", "Natan", 37);
        saveAndPrint(userService, "Clark", "Kent", 40);
        //Get all users in DB and print them
        System.out.println(userService.getAllUsers());
        //Clean and Delete table
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    public static void saveAndPrint(UserService userService, String name, String lastName, int age) {
        userService.saveUser(name, lastName, (byte) age);
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }


}
