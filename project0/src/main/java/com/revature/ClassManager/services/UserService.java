package com.revature.ClassManager.services;

import com.revature.ClassManager.documents.AppUser;
import com.revature.ClassManager.repos.UserRepository;
import com.revature.ClassManager.util.UserSession;
import com.revature.ClassManager.util.exceptions.AuthenticationException;
import com.revature.ClassManager.util.exceptions.InvalidRequestException;
import com.revature.ClassManager.util.exceptions.ResourcePersistenceException;

public class UserService {

    private final UserRepository userRepo;
    private final UserSession session;

    public UserService(UserRepository userRepo, UserSession session) {
        this.userRepo = userRepo;
        this.session = session;
    }

    public UserSession getSession() {
        return session;
    }

    public AppUser register(AppUser newUser) {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        if (userRepo.findUserByUsername(newUser.getUsername()) != null) {
            throw new ResourcePersistenceException("Provided username is already taken!");
        }

        if (userRepo.findUserByEmail(newUser.getEmail()) != null) {
            throw new ResourcePersistenceException("Provided email is already taken!");
        }

            return userRepo.save(newUser);
    }

    public AppUser registerAdmin(AppUser newUser) {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        if (userRepo.findUserByUsername(newUser.getUsername()) != null) {
            throw new ResourcePersistenceException("Provided username is already taken!");
        }

        if (userRepo.findUserByEmail(newUser.getEmail()) != null) {
            throw new ResourcePersistenceException("Provided email is already taken!");
        }

        return userRepo.saveAdmin(newUser);
    }

    public AppUser login(String username, String password, String type) {

        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid user credentials provided!");
        }

        AppUser authUser = userRepo.findUserByCredentials(username, password, type);

        if (authUser == null) {
            throw new AuthenticationException("Invalid credentials provided!");
        }

        session.setCurrentUser(authUser);

        return authUser;

    }

    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

}
