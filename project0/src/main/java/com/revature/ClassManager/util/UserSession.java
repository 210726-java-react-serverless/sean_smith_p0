package com.revature.ClassManager.util;

import com.revature.ClassManager.documents.AppUser;

public class UserSession {

    private AppUser currentUser;

    public AppUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isActive() {
        return currentUser != null;
    }

    public void closeSession() {
        setCurrentUser(null);
    }

}
