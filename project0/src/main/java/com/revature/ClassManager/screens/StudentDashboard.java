package com.revature.ClassManager.screens;
import com.revature.ClassManager.documents.AppUser;
import com.revature.ClassManager.documents.registrationCatalog;
import com.revature.ClassManager.services.UserService;
import com.revature.ClassManager.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class StudentDashboard extends Screen {

    private final Logger logger = LogManager.getLogger(StudentDashboard.class);
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
        boolean leaveDashboard = false; //checks if user chose to leave dashboard
        String studentName = currentUser.getFirstName() + " " + currentUser.getLastName();
        //sets current student's name to full name of current user.
        //used to put name on class roster during registration

        while(leaveDashboard == false){
            System.out.println("1. Show Available Classes");
            System.out.println("2. Show Currently Registered");
            System.out.println("3. Register for Class");
            System.out.println("4. Withdraw from Class");
            System.out.println("5. Exit Dashboard");
            try{
                int userChoice = Integer.parseInt(consoleReader.readLine());

                if(userChoice == 1){
                    String className = "test";
                    //didn't feel like making a new method for this case, so i just used a dummy value
                    //to make a registrationCatalog object
                    registrationCatalog newClass = new registrationCatalog(className);
                    newClass.showClasses();
                }
                else if(userChoice == 2){
                    currentUser.getRegisteredClasses().clear();
                    //clear currentUser's registeredClasses list so there aren't duplicate values
                    //also satisfies need to remove any values from registeredClasses list during withdraw
                    registrationCatalog newClass = new registrationCatalog(studentName);
                    List<String> allClasses = new ArrayList<>();
                    newClass.getAllCollections(allClasses);
                    //gets string list of all available classes on DB
                    for(int i = 0; i < allClasses.size(); i++){
                        if(newClass.currentlyRegistered(newClass, false, allClasses.get(i)) == true){
                            currentUser.setRegisteredClasses(currentUser.getRegisteredClasses(), allClasses.get(i));
                            //checks if user is registered to any classes, and puts it in registeredClasses list if they are
                        }
                    }
                    System.out.println(currentUser.getRegisteredClasses());
                }
                else if(userChoice == 3){
                    System.out.println("Class Name: ");
                    String className = consoleReader.readLine();
                    registrationCatalog newClass = new registrationCatalog(studentName);
                    if(newClass.currentlyRegistered(newClass, false, className) != true){
                        newClass.register(newClass, className); //adds studentName to class' collection in DB
                    }else{
                        System.out.println("Already registered for this class!");
                    }

                }
                else if(userChoice == 4){
                    System.out.println("Choose Class to Withdraw From: ");
                    String className = consoleReader.readLine();
                    registrationCatalog newClass = new registrationCatalog(studentName);
                    newClass.withdraw(newClass, className); //removes studentName from class' collection in DB
                }
                else if(userChoice == 5){
                    leaveDashboard = true; //breaks loop in order to return to welcome screen
                }
                else{
                    System.out.println("Invalid Option");
                }
            }catch (Exception e){
                logger.error(e.getMessage()); //checks if user input a non int value
                logger.debug("Invalid Input");
            }

    }
        System.out.println("Returning to Welcome Screen.");
        router.navigate("/welcome");
    }
}
