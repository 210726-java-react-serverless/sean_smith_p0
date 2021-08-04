package com.revature.studentmanager.models;

import java.util.ArrayList;
import java.util.List;

public class classCatalog {

    private String className;
    private boolean isRegistered;
    private int maxStudents;
    private List<String> registeredStudents = new ArrayList<String>();;

    public classCatalog(String className, int maxStudents){
        this.className = className;
        this.maxStudents = maxStudents;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public List<String> getRegisteredStudents(classCatalog thisClass) {
        System.out.println(thisClass.registeredStudents);
        return thisClass.registeredStudents;
    }

    public void setRegisteredStudents(classCatalog thisClass, String studentName) {
        thisClass.registeredStudents.add(studentName);
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }
}
