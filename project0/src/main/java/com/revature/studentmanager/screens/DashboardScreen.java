package com.revature.studentmanager.screens;

import com.revature.studentmanager.models.classCatalog;
import com.revature.studentmanager.util.ScreenRouter;

import java.io.BufferedReader;
import java.io.IOException;

public class DashboardScreen extends Screen {

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        System.out.println("Welcome to Dashboard\n");
        System.out.println("Create New Class: ");
        String className = consoleReader.readLine(); //TODO check if valid name

        System.out.println("Enter Class Size: "); //TODO check if int value
        int classSize = Integer.parseInt(consoleReader.readLine());

        classCatalog newClass = new classCatalog(className,classSize);

        System.out.println("Class successfully created.");
        System.out.println("Register for Class: ");
        String newName = consoleReader.readLine();
        newClass.setRegisteredStudents(newClass, newName);
        newClass.getRegisteredStudents(newClass);
        router.navigate("/welcome");
    }

}
