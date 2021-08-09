package com.revature.ClassManager.screens;

import com.revature.ClassManager.documents.AppUser;
import com.revature.ClassManager.documents.registrationCatalog;
import com.revature.ClassManager.services.UserService;
import com.revature.ClassManager.util.ScreenRouter;

import java.io.BufferedReader;

public class TeacherDashboard extends Screen {

    private final UserService userService;

    public TeacherDashboard(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("TeacherDashboard", "/teacherdashboard", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {

        if (!userService.getSession().isActive()) {
            System.out.println("Session invalidated, navigating back to welcome screen...");
            router.navigate("/welcome");
            return;
        }

        AppUser currentUser = userService.getSession().getCurrentUser();

        System.out.println("Welcome to your dashboard, " + currentUser.getUsername());
        boolean leaveDashboard = false;


        while(leaveDashboard == false){
        System.out.println("1. Show Available Classes");
        System.out.println("2. Show Class Rosters");
        System.out.println("3. Add New Class");
        System.out.println("4. Delete Class");
        System.out.println("5. Exit Dashboard");
        int userChoice = Integer.parseInt(consoleReader.readLine());

            if(userChoice == 1){
                String className = "test";
                registrationCatalog newClass = new registrationCatalog(className);
                newClass.showClasses();
            }
            else if(userChoice == 2){
                System.out.println("Enter Class Name: ");
                String className = consoleReader.readLine();
                registrationCatalog newClass = new registrationCatalog(className);
                newClass.showRoster(newClass, className);
            }
            else if(userChoice == 3){
                System.out.println("Class Name: ");
                String className = consoleReader.readLine();
                System.out.println("Class Size: ");
                int classSize = Integer.parseInt(consoleReader.readLine());
                registrationCatalog newClass = new registrationCatalog(className, classSize);
                newClass.save(newClass, newClass.getClassName());
            }
            else if(userChoice == 4){
                System.out.println("Choose class to delete: ");
                String className = consoleReader.readLine();
                registrationCatalog newClass = new registrationCatalog(className);
                newClass.delete(newClass, className);
            }
            else if(userChoice == 5){
                leaveDashboard = true;
            }
            else{
                System.out.println("Invalid Option");
            }
        }

        System.out.println("Returning to Welcome Screen.");
        router.navigate("/welcome");
    }

}
