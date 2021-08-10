package com.revature.ClassManager.screens;
import com.revature.ClassManager.documents.AppUser;
import com.revature.ClassManager.documents.registrationCatalog;
import com.revature.ClassManager.services.UserService;
import com.revature.ClassManager.util.ScreenRouter;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

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
        String studentName = currentUser.getFirstName() + " " + currentUser.getLastName();

        while(leaveDashboard == false){
            System.out.println("1. Show Available Classes");
            System.out.println("2. Show Currently Registered");
            System.out.println("3. Register for Class");
            System.out.println("4. Withdraw from Class");
            System.out.println("5. Exit Dashboard");
            int userChoice = Integer.parseInt(consoleReader.readLine());

            if(userChoice == 1){
                String className = "test";
                registrationCatalog newClass = new registrationCatalog(className);
                newClass.showClasses();
            }
            else if(userChoice == 2){
                currentUser.getRegisteredClasses().clear();
                registrationCatalog newClass = new registrationCatalog(studentName);
                List<String> allClasses = new ArrayList<>();
                newClass.getAllCollections(allClasses);
                for(int i = 0; i < allClasses.size(); i++){
                    if(newClass.currentlyRegistered(newClass, false, allClasses.get(i)) == true){
                        currentUser.setRegisteredClasses(currentUser.getRegisteredClasses(), allClasses.get(i));
                    }
                }
                System.out.println(currentUser.getRegisteredClasses());
            }
            else if(userChoice == 3){
                System.out.println("Class Name: ");
                String className = consoleReader.readLine();
                registrationCatalog newClass = new registrationCatalog(studentName);
                newClass.register(newClass, className);
            }
            else if(userChoice == 4){
                System.out.println("Choose Class to Withdraw From: ");
                String className = consoleReader.readLine();
                registrationCatalog newClass = new registrationCatalog(studentName);
                newClass.withdraw(newClass, className);
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
