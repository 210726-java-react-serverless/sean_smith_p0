package com.revature.ClassManager.screens;

import com.revature.ClassManager.documents.AppUser;
import com.revature.ClassManager.documents.registrationCatalog;
import com.revature.ClassManager.services.UserService;
import com.revature.ClassManager.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {

    private final UserService userService;

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
        this.userService = userService;
    }

    /*
        TODO
         Implement a dashboard that displays the user's username and gives them the option
         to navigate to other screens (e.g. UserProfileScreen).
     */
    @Override
    public void render() throws Exception {

        System.out.println("DashboardScreen works!");

        if (!userService.getSession().isActive()) {
            System.out.println("Session invalidated, navigating back to welcome screen...");
            router.navigate("/welcome");
            return;
        }

        AppUser currentUser = userService.getSession().getCurrentUser();

        System.out.println("Welcome to your dashboard, " + currentUser.getUsername());

        System.out.println("Choose class to delete: ");
        //System.out.println("Class Name: ");
        String className = consoleReader.readLine();
        //System.out.println("Class Size: ");
        //int classSize = Integer.parseInt(consoleReader.readLine());

        //registrationCatalog newClass = new registrationCatalog(className, classSize);
        registrationCatalog newClass = new registrationCatalog(className);
        newClass.delete(newClass);
        //newClass.save(newClass);
        //newClass.find(newClass);


        System.out.println("Screen under construction, sending you back to the Welcome Screen.");
        router.navigate("/welcome");
    }

}
