package com.revature.ClassManager.util;

import com.revature.ClassManager.repos.UserRepository;
import com.revature.ClassManager.screens.*;
import com.revature.ClassManager.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppState {

    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {

        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        UserSession userSession = new UserSession();
        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo, userSession);


        router.addScreen(new WelcomeScreen(consoleReader, router))
              .addScreen(new LoginScreen(consoleReader, router, userService))
              .addScreen(new RegisterScreen(consoleReader, router, userService))
              .addScreen(new TeacherDashboard(consoleReader, router, userService))
                .addScreen(new StudentDashboard(consoleReader, router, userService))
              .addScreen(new UserProfileScreen(consoleReader, router));

    }

    public void startup() {
        router.navigate("/welcome");

        while (appRunning) {
            try {
                router.getCurrentScreen().render();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Switches 'appRunning' to false, ending the while loop and
    // closing the app through super's main.
    public static void shutdown() {
        appRunning = false;
        MongoClientFactory.getInstance().cleanUp();
    }

}
