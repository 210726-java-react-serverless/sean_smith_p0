package com.revature.studentmanager.models;

public class classCatalog {

    private String className;
    private boolean isRegistered;
    private int maxStudents;

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

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }
}
