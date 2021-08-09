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

        boolean userChose = false;
        boolean choseExit = false;
        userChose = false;
        int choice = 0;
        while(userChose == false){
            System.out.println("\nChoose Account Type\n" +
                    "1. Student\n" +
                    "2. Teacher\n" +
                    "3. Go Back");
            System.out.print("> ");
            try{
            int userChoice = Integer.parseInt(consoleReader.readLine());
            if(userChoice == 1){
                userChose = true;
                choice = 1;
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
                System.out.println("Invalid Input");
            }
        }

        if(choseExit!=true){
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

            if(choice == 2){
                try {
                    userService.registerAdmin(newUser);
                    logger.info("Teacher successfully registered!");
                    router.navigate("/dashboard");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    logger.debug("User not registered!");
                    router.navigate("/welcome");
                }
            }
            else{
                try {
                    userService.register(newUser);
                    logger.info("Student successfully registered!");
                    router.navigate("/dashboard");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    logger.debug("User not registered!");
                    router.navigate("/welcome");
                }
            }
        }
        else{
            router.goToPrevious();
        }

    }

}
