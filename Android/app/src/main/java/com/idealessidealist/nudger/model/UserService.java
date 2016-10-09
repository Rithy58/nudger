package com.idealessidealist.nudger.model;

/**
 * Created by Kenny Tsui on 10/9/2016.
 */

public class UserService {

    private static User user;

    public static UserService getInstance() {
        return new UserService();
    }

    private UserService() {

    }

    public void setUser(User user) {
        UserService.user = user;
    }

    public User getUser() {
        return user;
    }

}
