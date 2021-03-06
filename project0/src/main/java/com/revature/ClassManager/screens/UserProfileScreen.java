package com.revature.ClassManager.screens;

import com.revature.ClassManager.util.ScreenRouter;

import java.io.BufferedReader;

public class UserProfileScreen extends Screen {

    public UserProfileScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("UserProfileScreen", "/profile", consoleReader, router);
    }

    /*
        TODO
         Implement a profile screen that displays that can be used to display and update
         user profile information.
     */
    @Override
    public void render() throws Exception {
        System.out.println("UserProfileScreen works!");
        System.out.println("Screen under construction, sending you back to the Dashboard Screen.");
        router.navigate("/dashboard");
    }

}
