package com.util;

import com.datasourse.repos.RegistrationCatalog;
import com.datasourse.repos.UserRepository;
import com.mongodb.client.MongoClient;
import com.services.UserService;
import com.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //try{
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            UserRepository userRepository = new UserRepository();
            UserService userService = new UserService(userRepository);
            UserServlet userServlet = new UserServlet(userService);
            ServletContext context =  sce.getServletContext();
            context.addServlet("UserServlet" , userServlet).addMapping("/users/*");
      //  }catch ()
      //  {

       // }


    }
}
