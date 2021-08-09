package com.revature.ClassManager.screens;
import com.revature.ClassManager.documents.AppUser;
import com.revature.ClassManager.services.UserService;
import com.revature.ClassManager.util.ScreenRouter;

import java.io.BufferedReader;

public class StudentDashboard extends Screen {

    private final UserService userService;

    public StudentDashboard(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("StudentDashboard", "/studentdashboard", consoleReader, router);
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
            System.out.println("1. Show Current Classes");
            System.out.println("2. Register for Class");
            System.out.println("3. Withdraw from Class");
            System.out.println("4. Exit Dashboard");
            int userChoice = Integer.parseInt(consoleReader.readLine());

            if(userChoice == 1){

            }
            else if(userChoice == 2){
                /*
                String fullname = currentUser.getFirstName() + " " + currentUser.getLastName();
                System.out.println("Class Name: ");
                String className = consoleReader.readLine();
                registrationCatalog newClass1 = new registrationCatalog(className, fullname);
                registrationCatalog newClass2 = new registrationCatalog(className);
                newClass2.delete(newClass2);
                newClass1.register(newClass1, fullname);*/
            }
            else if(userChoice == 3){

            }
            else if(userChoice == 4){
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
