package com.revature.ClassManager.screens;

import com.revature.ClassManager.documents.AppUser;
import com.revature.ClassManager.services.UserService;
import com.revature.ClassManager.util.ScreenRouter;
import com.revature.ClassManager.util.exceptions.AuthenticationException;

import java.io.BufferedReader;

public class LoginScreen extends Screen {

    private final UserService userService;

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("LoginScreen", "/login", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {

        boolean userChose = false;
        while(userChose == false){
            System.out.println("\nUser Login\n" +
                    "1. Student Account\n" +
                    "2. Teacher Account\n" +
                    "3. Go Back");
            System.out.print("> ");
            try{
                int userChoice = Integer.parseInt(consoleReader.readLine());
                if(userChoice == 1){
                    String type = "users";
                    userChose = true;
                    System.out.print("Username: ");
                    String username = consoleReader.readLine();

                    System.out.print("Password: ");
                    String password = consoleReader.readLine();
                    try {
                        AppUser authUser = userService.login(username, password, type);
                        System.out.println("Login successful!");
                        router.navigate("/studentdashboard");
                    } catch (AuthenticationException ae) {
                        System.out.println("No user found with provided credentials!");
                        System.out.println("Navigating back to welcome screen...");
                        router.navigate("/welcome");
                    }
                }
                if(userChoice == 2){
                    String type = "admin";
                    userChose = true;
                    System.out.print("Username: ");
                    String username = consoleReader.readLine();

                    System.out.print("Password: ");
                    String password = consoleReader.readLine();
                    try {
                        AppUser authUser = userService.login(username, password, type);
                        System.out.println("Login successful!");
                        router.navigate("/teacherdashboard");
                    } catch (AuthenticationException ae) {
                        System.out.println("No user found with provided credentials!");
                        System.out.println("Navigating back to welcome screen...");
                        router.navigate("/welcome");
                    }

                }
                else if(userChoice == 3){
                    userChose = true;
                    router.goToPrevious();
                }
                else{
                    System.out.println("Invalid Option");
                }
            }catch(Exception e){
                System.out.println("Invalid Input");
            }

        }


    }

}
