package com.servlets;

import com.services.UserService;

import javax.servlet.http.HttpServlet;

public class UserServlet extends HttpServlet {

    private final UserService userService;
    public UserServlet(UserService userService)
    {
        this.userService = userService;
    }


}
