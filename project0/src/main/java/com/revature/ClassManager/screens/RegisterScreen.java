package com.revature.ClassManager.screens;

import com.revature.ClassManager.documents.AppUser;
import com.revature.ClassManager.services.UserService;
import com.revature.ClassManager.util.ScreenRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class RegisterScreen extends Screen {

    private final Logger logger = LogManager.getLogger(RegisterScreen.class);
    private final UserService userService;

    public RegisterScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("RegisterScreen", "/register", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {

        boolean userChose = false; //checks if user made a valid choice (1,2, or 3)
        boolean choseExit = false; //will loop until user makes valid choice
        userChose = false;
        int choice = 0;
        while(userChose == false){ //start of loop
            System.out.println("\nChoose Account Type\n" +
                    "1. Student\n" +
                    "2. Teacher\n" +
                    "3. Go Back");
            System.out.print("> ");
            try{
            int userChoice = Integer.parseInt(consoleReader.readLine());
            if(userChoice == 1){
                userChose = true;
                //breaks out of loop if user made valid choice
                choice = 1;
                //sets user choice for later
            }
            else if(userChoice == 2){
                userChose = true;
                choice = 2;
            }
            else if(userChoice == 3){
                userChose = true;
                choseExit = true;
            }
            else{
                System.out.println("Invalid Option");
            }
            }catch(Exception e){
                //exception if user inputs a non int value, such as a string
                logger.error(e.getMessage());
                System.out.println("Invalid Input");
            }
        } //end of loop

        //if user chose option 3, skips this and returns to previous screen
        if(choseExit!=true){
            //get user credentials
            System.out.print("First name: ");
            String firstName = consoleReader.readLine();

            System.out.print("Last name: ");
            String lastName = consoleReader.readLine();

            System.out.print("Email: ");
            String email = consoleReader.readLine();

            System.out.print("Username: ");
            String username = consoleReader.readLine();

            System.out.print("Password: ");
            String password = consoleReader.readLine();

            AppUser newUser = new AppUser(firstName, lastName, email, username, password);

            //only potential options are 1 or 2. register teacher for 2 and student for 1
            if(choice == 2){
                try {
                    userService.registerAdmin(newUser); //registerAdmin saves credentials to teacher collection
                    logger.info("Teacher successfully registered!");
                } catch (Exception e) {
                    logger.error(e.getMessage()); //unexpected error: return to welcome screen
                    logger.debug("User not registered!");
                    router.navigate("/welcome");
                }
            }
            else{
                try {
                    userService.register(newUser); //register saves credentials to student collection
                    logger.info("Student successfully registered!"); //TODO prevent duplicate registrations
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    logger.debug("User not registered!");
                    router.navigate("/welcome");
                }
            }
        }
        else{
            //returns to previous screen
            router.goToPrevious();
        }

    }

}
